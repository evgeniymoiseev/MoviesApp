package com.example.moviesapp.repository

import com.example.moviesapp.api.RetrofitInstance
import com.example.moviesapp.db.MovieDatabase

class MovieRepository(
    private val db: MovieDatabase
) {

    suspend fun getMostPopularMovies(lang: String = "en") =
        RetrofitInstance.api.getMostPopularMovies(lang)

    suspend fun getMovieById(id: String, lang: String = "en") =
        RetrofitInstance.api.getMovieById(lang, id)

    fun getDatabaseMovies() = db.getMoviesDao().getMovies()
}