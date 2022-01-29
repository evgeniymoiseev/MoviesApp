package com.example.moviesapp.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("errorMessage") val errorMessage: String,
    @SerializedName("items") val movies: List<Movie>
)