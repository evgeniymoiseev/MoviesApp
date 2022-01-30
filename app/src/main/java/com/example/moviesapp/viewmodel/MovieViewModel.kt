package com.example.moviesapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.MoviesResponse
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MovieViewModel(
    app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    private val _movies: MutableLiveData<Resource<MoviesResponse>> = MutableLiveData()
    val movies get() = _movies

    init {
        getMostPopularMovies()
    }

    private fun getMostPopularMovies() = viewModelScope.launch {
        _movies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val retrofitResponse = repository.getMostPopularMovies()

                _movies.postValue(handleMovieResponse(retrofitResponse))
            } else {
                _movies.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _movies.postValue(Resource.Error("Network Failure"))
                else -> _movies.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleMovieResponse(retrofitResponse: Response<MoviesResponse>): Resource<MoviesResponse> {
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { moviesResponse ->
                return Resource.Success(moviesResponse)
            }
        }
        return Resource.Error(retrofitResponse.message())
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<MovieApplication>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}