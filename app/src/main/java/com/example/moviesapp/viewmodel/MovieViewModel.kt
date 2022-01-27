package com.example.moviesapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.net.TransportInfo
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.Movie
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.ResponseWrapper
import kotlinx.coroutines.launch

class MovieViewModel(
    app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    val movies: MutableLiveData<ResponseWrapper<Movie>> = MutableLiveData()

    init {
        getMostPopularMovies()
    }

    private fun getMostPopularMovies() = viewModelScope.launch {

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