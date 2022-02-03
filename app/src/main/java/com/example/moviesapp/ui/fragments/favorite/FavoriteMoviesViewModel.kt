package com.example.moviesapp.ui.fragments.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.repository.MovieRepository
import kotlinx.coroutines.launch

class FavoriteMoviesViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    fun getFavoriteMovies() = repository.getDatabaseMovies()

    fun deleteFavoriteMovie(id: String) = viewModelScope.launch {
        repository.deleteFavoriteMovie(id)
    }
}