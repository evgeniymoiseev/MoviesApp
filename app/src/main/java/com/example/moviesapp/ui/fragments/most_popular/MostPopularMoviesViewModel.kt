package com.example.moviesapp.ui.fragments.most_popular

import android.app.Application
import androidx.lifecycle.*
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.most_popular_movies.MostPopularMoviesResponse
import com.example.moviesapp.model.most_popular_movies.SimpleMovie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Resource
import com.example.moviesapp.util.hasInternetConnection
import com.example.moviesapp.util.mappers.SimpleMovieMapper
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class MostPopularMoviesViewModel(
    app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    val favoriteMoviesIdListLiveData: LiveData<List<String>> =
        Transformations.map(repository.getDatabaseMovies()) { listFavoriteMovies ->
            listFavoriteMovies.map { favoriteMovie -> favoriteMovie.id }
        }
    private var favoriteMoviesIdList = emptyList<String>()
    private val favoriteMoviesIdListObserver = Observer<List<String>> { currentIds ->
        favoriteMoviesIdList = currentIds
        _mostPopularMovies.postValue(updateMostPopularMovies())
    }

    private val _mostPopularMovies: MutableLiveData<Resource<List<SimpleMovie>>> =
        MutableLiveData()
    val mostPopularMovies get() = _mostPopularMovies

    init {
        getMostPopularMovies()
        favoriteMoviesIdListLiveData.observeForever(favoriteMoviesIdListObserver)
    }

    private fun getMostPopularMovies() = viewModelScope.launch {
        _mostPopularMovies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MovieApplication>())) {
                val retrofitResponse = repository.getMostPopularMovies()
                _mostPopularMovies.postValue(handleMostPopularMovieResponse(retrofitResponse))
            } else {
                _mostPopularMovies.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _mostPopularMovies.postValue(Resource.Error("Network Failure"))
                else -> _mostPopularMovies.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleMostPopularMovieResponse(retrofitResponse: Response<MostPopularMoviesResponse>)
            : Resource<List<SimpleMovie>> {
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { body ->
                return if (body.errorMessage.isNotEmpty()) {
                    Resource.Error(body.errorMessage)
                } else {
                    Resource.Success(body.mostPopularMovies.map { mostPopularMovie ->
                        val isInFavorites = mostPopularMovie.id in favoriteMoviesIdList
                        SimpleMovieMapper(isInFavorites).map(mostPopularMovie)
                    })
                }
            }
        }
        return Resource.Error(retrofitResponse.message())
    }

    fun saveFavoriteMovie(movie: SimpleMovie) = viewModelScope.launch {
        repository.saveFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(id: String) = viewModelScope.launch {
        repository.deleteFavoriteMovie(id)
    }

    private fun updateMostPopularMovies(): Resource<List<SimpleMovie>> {
        _mostPopularMovies.value?.data?.let {
            val newList = it.toMutableList()
            for (i in newList.indices) {
                if (newList[i].id in favoriteMoviesIdList) {
                    newList[i] = newList[i].copy(isFavorite = true)
                } else {
                    newList[i] = newList[i].copy(isFavorite = false)
                }
            }
            return Resource.Success(newList)
        }
        return Resource.Loading()
    }

    override fun onCleared() {
        super.onCleared()
        favoriteMoviesIdListLiveData.removeObserver(favoriteMoviesIdListObserver)
    }
}