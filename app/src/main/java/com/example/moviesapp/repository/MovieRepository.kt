package com.example.moviesapp.repository

import com.example.moviesapp.api.RetrofitInstance
import com.example.moviesapp.db.MovieDatabase
import com.example.moviesapp.model.most_popular_movies.SimpleMovie

class MovieRepository(
    private val db: MovieDatabase
) {

    suspend fun getMostPopularMovies(lang: String = "en") =
        RetrofitInstance.api.getMostPopularMovies(lang)

    suspend fun getMovieById(id: String, lang: String = "en") =
        RetrofitInstance.api.getMovieById(lang, id)

    fun getDatabaseMovies() = db.getMoviesDao().getMovies()

    suspend fun saveFavoriteMovie(movie: SimpleMovie) = db.getMoviesDao().insert(movie)

    suspend fun deleteFavoriteMovie(id: String) = db.getMoviesDao().delete(id)
}