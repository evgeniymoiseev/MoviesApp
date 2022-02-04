package com.example.moviesapp.ui.fragments.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.local.ExtendedMovie
import com.example.moviesapp.model.network.NetworkExtendedMovie
import com.example.moviesapp.model.local.SimpleMovie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Event
import com.example.moviesapp.util.hasInternetConnection
import com.example.moviesapp.util.mappers.NetworkExtendedToLocalExtendedMapper
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DetailMovieViewModel(
    app: Application,
    private val repository: MovieRepository,
    val id: String,
) : AndroidViewModel(app) {

    val movieInDatabase = repository.getDatabaseMovieById(id)

    private val _movieEvent: MutableLiveData<Event<ExtendedMovie>> = MutableLiveData()
    val movieResource get() = _movieEvent as LiveData<Event<ExtendedMovie>>

    init {
        getMovieById(id)
    }

    fun saveMovie(movie: SimpleMovie) = viewModelScope.launch {
        repository.saveFavoriteMovie(movie)
    }

    private fun getMovieById(id: String) = viewModelScope.launch {
        _movieEvent.postValue(Event.Loading())
        try {
            if (hasInternetConnection(getApplication<MovieApplication>())) {
                val retrofitResponse = repository.getMovieById(id)
                _movieEvent.postValue(handleMovieResponse(retrofitResponse))
            } else {
                _movieEvent.postValue(Event.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _movieEvent.postValue(
                    Event.Error("Network Failure")
                )
                else -> _movieEvent.postValue(Event.Error("Conversion Error"))
            }
        }
    }

    private fun handleMovieResponse(retrofitResponse: Response<NetworkExtendedMovie>)
            : Event<ExtendedMovie> {
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { body ->
                return if (!body.errorMessage.isNullOrEmpty()) {
                    Event.Error(body.errorMessage)
                } else {
                    Event.Success(NetworkExtendedToLocalExtendedMapper().map(body))
                }
            }
        }
        return Event.Error(retrofitResponse.message())
    }
}