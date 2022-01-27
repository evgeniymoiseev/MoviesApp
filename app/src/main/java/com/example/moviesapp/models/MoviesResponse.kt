package com.example.moviesapp.models

data class MoviesResponse(
    val errorMessage: String,
    val movies: List<Movie>
)