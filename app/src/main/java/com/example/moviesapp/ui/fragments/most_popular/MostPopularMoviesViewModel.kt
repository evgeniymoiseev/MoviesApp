package com.example.moviesapp.ui.fragments.most_popular

import android.app.Application
import androidx.lifecycle.*
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.MostPopularMoviesResponse
import com.example.moviesapp.model.SimpleMovie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Resource
import com.example.moviesapp.util.hasInternetConnection
import com.example.moviesapp.util.mappers.SimpleMovieMapper
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MostPopularMoviesViewModel(
    app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    private val favoriteDatabaseMoviesLiveData = repository.getDatabaseMovies()
    private var favoriteDatabaseMoviesList = emptyList<SimpleMovie>()
    private val favoriteDatabaseMoviesObserver = Observer<List<SimpleMovie>> { databaseMovies ->
        favoriteDatabaseMoviesList = databaseMovies
        _mostPopularMovies.postValue(updateMostPopularMovies())
    }

    private val _mostPopularMovies: MutableLiveData<Resource<List<SimpleMovie>>> =
        MutableLiveData()
    val mostPopularMovies get() = _mostPopularMovies as LiveData<Resource<List<SimpleMovie>>>

    init {
        getMostPopularMovies()
        favoriteDatabaseMoviesLiveData.observeForever(favoriteDatabaseMoviesObserver)
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
                    val databaseMoviesIds = favoriteDatabaseMoviesList.map { it.id }
                    Resource.Success(body.networkSimpleMovies.map { mostPopularMovie ->
                        val isInFavorites = mostPopularMovie.id in databaseMoviesIds
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
        _mostPopularMovies.value?.data?.let { listMovies ->
            val newList = listMovies.toMutableList()
            val databaseMoviesIds = favoriteDatabaseMoviesList.map { it.id }
            for (i in newList.indices) {
                val isInFavorites = newList[i].id in databaseMoviesIds
                newList[i] = newList[i].copy(isFavorite = isInFavorites)
            }
            return Resource.Success(newList)
        }
        return Resource.Loading()
    }

    override fun onCleared() {
        super.onCleared()
        favoriteDatabaseMoviesLiveData.removeObserver(favoriteDatabaseMoviesObserver)
    }
}