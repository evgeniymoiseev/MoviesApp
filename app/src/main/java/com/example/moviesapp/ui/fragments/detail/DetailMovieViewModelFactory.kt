package com.example.moviesapp.ui.fragments.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.repository.MovieRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DetailMovieViewModelFactory @AssistedInject constructor(
    private val application: Application,
    private val repository: MovieRepository,
    @Assisted("movieId") private val id: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailMovieViewModel(application, repository, id) as T
    }

    @AssistedFactory
    interface AssistFactory {
        fun create(@Assisted("movieId") movieId: String): DetailMovieViewModelFactory
    }
}