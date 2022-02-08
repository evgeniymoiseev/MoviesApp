package com.example.moviesapp.repository

import com.example.moviesapp.api.MoviesApi
import com.example.moviesapp.db.MovieDatabase
import com.example.moviesapp.model.local.SimpleMovie

class MovieRepository(
    private val db: MovieDatabase,
    private val api: MoviesApi
) {

    suspend fun getMostPopularMovies(lang: String = "en") =
        api.getMostPopularMovies(lang)

    suspend fun getMovieById(id: String, lang: String = "en") =
        api.getMovieById(lang, id)

    suspend fun searchMovies(expression: String, lang: String = "en") =
        api.searchMovies(lang, expression)

    fun getDatabaseMovies() = db.getMoviesDao().getMovies()

    fun getDatabaseMovieById(id: String) = db.getMoviesDao().getMovieById(id)

    suspend fun saveFavoriteMovie(movie: SimpleMovie) = db.getMoviesDao().insert(movie)

    suspend fun deleteFavoriteMovie(id: String) = db.getMoviesDao().delete(id)

}