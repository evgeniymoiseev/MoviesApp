package com.example.moviesapp.model

data class ExtendedMovie(
    val id: String,
    val title: String,
    val year: String,
    val runtime: String,
    val poster: String,
    val isEmptyRating: Boolean,
    val rating: String,
    val directors: String,
    val stars: String,
    val plot: String
)
