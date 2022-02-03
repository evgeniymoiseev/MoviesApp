package com.example.moviesapp.ui.fragments.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.ExtendedMovie
import com.example.moviesapp.model.NetworkExtendedMovie
import com.example.moviesapp.model.SimpleMovie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Resource
import com.example.moviesapp.util.hasInternetConnection
import com.example.moviesapp.util.mappers.ExtendedMovieMapper
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class DetailMovieViewModel(
    app: Application,
    private val repository: MovieRepository,
    val id: String,
) : AndroidViewModel(app) {

    val movieInDatabase = repository.getDatabaseMovieById(id)

    private val _movieResource: MutableLiveData<Resource<ExtendedMovie>> = MutableLiveData()
    val movieResource get() = _movieResource as LiveData<Resource<ExtendedMovie>>

    init {
        getMovieById(id)
    }

    private fun getMovieById(id: String) = viewModelScope.launch {
        _movieResource.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MovieApplication>())) {
                val retrofitResponse = repository.getMovieById(id)
                _movieResource.postValue(handleMovieResponse(retrofitResponse))
            } else {
                _movieResource.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _movieResource.postValue(
                    Resource.Error("Network Failure")
                )
                else -> _movieResource.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleMovieResponse(retrofitResponse: Response<NetworkExtendedMovie>)
            : Resource<ExtendedMovie> {
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { body ->
                return if (!body.errorMessage.isNullOrEmpty()) {
                    Resource.Error(body.errorMessage)
                } else {
                    Resource.Success(ExtendedMovieMapper().map(body))
                }
            }
        }
        return Resource.Error(retrofitResponse.message())
    }
}