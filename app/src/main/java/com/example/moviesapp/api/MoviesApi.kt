package com.example.moviesapp.api

import com.example.moviesapp.model.MoviesResponse
import com.example.moviesapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("{lang}/API/MostPopularMovies/${API_KEY}")
    suspend fun getMostPopularMovies(
        @Path("lang") lang: String
    ): Response<MoviesResponse>
}