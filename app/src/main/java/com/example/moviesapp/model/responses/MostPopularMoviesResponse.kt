package com.example.moviesapp.model.responses

import com.example.moviesapp.model.network.NetworkSimpleMovie
import com.google.gson.annotations.SerializedName

data class MostPopularMoviesResponse(
    val errorMessage: String,
    @SerializedName("items") val networkSimpleMovies: List<NetworkSimpleMovie>
)