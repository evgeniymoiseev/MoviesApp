package com.example.moviesapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.moviesapp.repository.MovieRepository

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {
}