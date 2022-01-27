package com.example.moviesapp.model

data class MoviesResponse(
    val errorMessage: String,
    val movies: List<Movie>
)