package com.example.moviesapp.ui.fragments.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.repository.MovieRepository

class FavoriteMoviesViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteMoviesViewModel(repository) as T
    }

}