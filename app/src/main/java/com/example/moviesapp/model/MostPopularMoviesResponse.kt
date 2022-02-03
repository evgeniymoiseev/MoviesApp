package com.example.moviesapp.model

import com.google.gson.annotations.SerializedName

data class MostPopularMoviesResponse(
    @SerializedName("errorMessage") val errorMessage: String,
    @SerializedName("items") val networkSimpleMovies: List<NetworkSimpleMovie>
)