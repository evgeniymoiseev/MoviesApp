package com.example.moviesapp.model.most_popular_movies

import com.google.gson.annotations.SerializedName

data class MostPopularMoviesResponse(
    @SerializedName("errorMessage") val errorMessage: String,
    @SerializedName("items") val mostPopularMovies: List<MostPopularMovie>
)