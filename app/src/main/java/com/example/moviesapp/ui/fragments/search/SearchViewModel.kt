package com.example.moviesapp.ui.fragments.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.model.local.ShortMovie
import com.example.moviesapp.model.responses.SearchMoviesResponse
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.util.Constants.Companion.SEARCH_DELAY
import com.example.moviesapp.util.Event
import com.example.moviesapp.util.hasInternetConnection
import com.example.moviesapp.util.mappers.NetworkShortToLocalShortMapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class SearchViewModel(
    app: Application,
    private val repository: MovieRepository
) : AndroidViewModel(app) {

    private var job: Job? = null
    private val _searchEvent: MutableLiveData<Event<List<ShortMovie>>> = MutableLiveData()
    val searchEvent get() = _searchEvent as LiveData<Event<List<ShortMovie>>>

    fun searchMovies(expression: String) {
        job?.cancel()
        job = viewModelScope.launch {
            _searchEvent.postValue(Event.Loading())
            delay(SEARCH_DELAY)
            try {
                if (hasInternetConnection(getApplication<MovieApplication>())) {
                    Timber.d("request expression = $expression")
                    val retrofitResponse = repository.searchMovies(expression)
                    Timber.d("response")
                    _searchEvent.postValue(handleSearchMovieResponse(retrofitResponse))
                } else {
                    _searchEvent.postValue(Event.Error("No internet connection"))
                }
            } catch (t: Throwable) {
                when (t) {
                    is IOException -> _searchEvent.postValue(Event.Error("Network Failure"))
                    else -> _searchEvent.postValue(Event.Error("Conversion Error"))
                }
            }
        }
    }

    private fun handleSearchMovieResponse(retrofitResponse: Response<SearchMoviesResponse>):
            Event<List<ShortMovie>> {
        Timber.d("start handling")
        if (retrofitResponse.isSuccessful) {
            retrofitResponse.body()?.let { body ->
                return if (body.errorMessage.isNotEmpty()) {
                    Event.Error(body.errorMessage)
                } else {
                    val mapper = NetworkShortToLocalShortMapper()
                    val listShortMovies = body.networkShortMovies.map { mapper.map(it) }
                    Timber.d("end handling")
                    Event.Success(listShortMovies)
                }
            }
        }
        return Event.Error(retrofitResponse.message())
    }
}