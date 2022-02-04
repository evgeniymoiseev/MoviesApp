package com.example.moviesapp.model.responses

import com.example.moviesapp.model.network.NetworkShortMovie
import com.google.gson.annotations.SerializedName

data class SearchMoviesResponse(
    val errorMessage: String,
    val expression: String,
    @SerializedName("results") val networkShortMovies: List<NetworkShortMovie>,
    val searchType: String
)