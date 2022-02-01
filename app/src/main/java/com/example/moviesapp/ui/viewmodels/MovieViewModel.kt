package com.example.moviesapp.ui.viewmodels

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
import com.example.moviesapp.model.most_popular_movies.MostPopularMoviesResponse
import com.example.moviesapp.model.movies.Movie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class MovieViewModel(
    app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    private val _movies: MutableLiveData<Resource<Movie>> = MutableLiveData()
    private val _mostPopularMovies: MutableLiveData<Resource<MostPopularMoviesResponse>> =
        MutableLiveData()

    val mostPopularMovies get() = _mostPopularMovies
    val movies get() = _movies

    init {
        getMostPopularMovies()
    }

    private fun getMostPopularMovies() = viewModelScope.launch {
        _mostPopularMovies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
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
            : Resource<MostPopularMoviesResponse> {
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { moviesResponse ->
                return if (moviesResponse.errorMessage.isNotEmpty()) {
                    Resource.Error(moviesResponse.errorMessage)
                } else {
                    Resource.Success(moviesResponse)
                }
            }
        }
        return Resource.Error(retrofitResponse.message())
    }

    fun getMovieById(id: String) = viewModelScope.launch {
        _movies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = repository.getMovieById(id)
                _movies.postValue(handleMovieResponse(response))
            } else {
                _movies.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> _mostPopularMovies.postValue(Resource.Error("Network Failure"))
                else -> _mostPopularMovies.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleMovieResponse(retrofitResponse: Response<Movie>)
            : Resource<Movie> {
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { movie ->
                return if (movie.errorMessage != null) {
                    Resource.Error(movie.errorMessage)
                } else Resource.Success(movie)
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