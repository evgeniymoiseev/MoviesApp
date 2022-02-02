package com.example.moviesapp.ui.fragments.most_popular

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.most_popular_movies.MostPopularMoviesResponse
import com.example.moviesapp.model.most_popular_movies.SimpleMovie
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

    private val _mostPopularMovies: MutableLiveData<Resource<List<SimpleMovie>>> =
        MutableLiveData()
    val mostPopularMovies get() = _mostPopularMovies

    init {
        getMostPopularMovies()
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
                    Resource.Success(body.mostPopularMovies.map {
                        SimpleMovieMapper().map(it)
                    })
                }
            }
        }
        return Resource.Error(retrofitResponse.message())
    }
}