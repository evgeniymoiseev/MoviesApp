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
import com.example.moviesapp.util.hasInternetConnection
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class MovieViewModel(
    app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    private val _movies: MutableLiveData<Resource<Movie>> = MutableLiveData()



    val movies get() = _movies







    fun getMovieById(id: String) = viewModelScope.launch {
        _movies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection(getApplication<MovieApplication>())) {
                val response = repository.getMovieById(id)
                _movies.postValue(handleMovieResponse(response))
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
}