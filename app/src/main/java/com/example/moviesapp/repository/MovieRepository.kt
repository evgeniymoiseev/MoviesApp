package com.example.moviesapp.repository

import com.example.moviesapp.api.RetrofitInstance

class MovieRepository() {

    suspend fun getMostPopularMovies(lang: String) =
        RetrofitInstance.api.getMostPopularMovies(lang)
}