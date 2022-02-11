package com.example.moviesapp.ui.fragments.most_popular

import android.app.Application
import androidx.lifecycle.*
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.local.SimpleMovie
import com.example.moviesapp.model.responses.MostPopularMoviesResponse
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Event
import com.example.moviesapp.util.extensions.toSimpleMovie
import com.example.moviesapp.util.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MostPopularViewModel(
    app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    private val favoriteDatabaseMoviesLiveData = repository.getDatabaseMovies()
    private var favoriteDatabaseMoviesList = emptyList<SimpleMovie>()
    private val favoriteDatabaseMoviesObserver = Observer<List<SimpleMovie>> { databaseMovies ->
        favoriteDatabaseMoviesList = databaseMovies
        _mostPopularMovies.postValue(updateMostPopularMovies())
    }

    private val _mostPopularMovies: MutableLiveData<Event<List<SimpleMovie>>> =
        MutableLiveData()
    val mostPopularMovies get() = _mostPopularMovies as LiveData<Event<List<SimpleMovie>>>

    init {
        favoriteDatabaseMoviesLiveData.observeForever(favoriteDatabaseMoviesObserver)
    }

    fun getMostPopularMovies() = viewModelScope.launch {
        _mostPopularMovies.postValue(Event.Loading())
        try {
            if (hasInternetConnection(getApplication<MovieApplication>())) {
                val retrofitResponse = repository.getMostPopularMovies()
                _mostPopularMovies.postValue(handleMostPopularMovieResponse(retrofitResponse))
            } else {
                _mostPopularMovies.postValue(Event.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _mostPopularMovies.postValue(Event.Error("Network Failure"))
                else -> _mostPopularMovies.postValue(Event.Error("Conversion Error"))
            }
        }
    }

    private fun handleMostPopularMovieResponse(retrofitResponse: Response<MostPopularMoviesResponse>)
            : Event<List<SimpleMovie>> {
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { body ->
                return if (body.errorMessage.isNotEmpty()) {
                    Event.Error(body.errorMessage)
                } else {
                    val databaseMoviesIds = favoriteDatabaseMoviesList.map { it.id }
                    Event.Success(body.networkSimpleMovies.map { mostPopularMovie ->
                        val isInFavorites = mostPopularMovie.id in databaseMoviesIds
                        mostPopularMovie.toSimpleMovie(isInFavorites)
                    })
                }
            }
        }
        return Event.Error(retrofitResponse.message())
    }

    fun saveFavoriteMovie(movie: SimpleMovie) = viewModelScope.launch {
        repository.saveFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(id: String) = viewModelScope.launch {
        repository.deleteFavoriteMovie(id)
    }

    private fun updateMostPopularMovies(): Event<List<SimpleMovie>> {
        _mostPopularMovies.value?.data?.let { listMovies ->
            val newList = listMovies.toMutableList()
            val databaseMoviesIds = favoriteDatabaseMoviesList.map { it.id }
            for (i in newList.indices) {
                val isInFavorites = newList[i].id in databaseMoviesIds
                newList[i] = newList[i].copy(isFavorite = isInFavorites)
            }
            return Event.Success(newList)
        }
        return Event.Loading()
    }

    override fun onCleared() {
        super.onCleared()
        favoriteDatabaseMoviesLiveData.removeObserver(favoriteDatabaseMoviesObserver)
    }
}