package com.example.moviesapp.repository

import com.example.moviesapp.api.RetrofitInstance

class MovieRepository() {

    suspend fun getMostPopularMovies(lang: String = "en") =
        RetrofitInstance.api.getMostPopularMovies(lang)

    suspend fun getMovieById(id: String, lang: String = "en") =
        RetrofitInstance.api.getMovieById(lang, id)
}