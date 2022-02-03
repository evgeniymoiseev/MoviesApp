package com.example.moviesapp.ui.fragments.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.repository.MovieRepository

class DetailMovieViewModelFactory(
    private val app: Application,
    private val repository: MovieRepository,
    private val id: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailMovieViewModel(app, repository, id) as T
    }
}