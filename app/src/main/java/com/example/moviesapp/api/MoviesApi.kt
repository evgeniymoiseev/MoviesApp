package com.example.moviesapp.api

import com.example.moviesapp.model.responses.MostPopularMoviesResponse
import com.example.moviesapp.model.network.NetworkExtendedMovie
import com.example.moviesapp.model.responses.SearchMoviesResponse
import com.example.moviesapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("{lang}/API/MostPopularMovies/${API_KEY}")
    suspend fun getMostPopularMovies(
        @Path("lang") lang: String
    ): Response<MostPopularMoviesResponse>

    @GET("{lang}/API/Title/${API_KEY}/{id}")
    suspend fun getMovieById(
        @Path("lang") lang: String,
        @Path("id") id: String
    ): Response<NetworkExtendedMovie>

    @GET("/{lang}/API/SearchMovie/${API_KEY}/{expression}")
    suspend fun searchMovies(
        @Path("lang") lang: String,
        @Path("expression") expression: String
    ): Response<SearchMoviesResponse>
}