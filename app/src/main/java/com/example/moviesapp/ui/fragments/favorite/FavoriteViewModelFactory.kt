package com.example.moviesapp.ui.fragments.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moviesapp.repository.MovieRepository

class FavoriteViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteViewModel(repository) as T
    }

}