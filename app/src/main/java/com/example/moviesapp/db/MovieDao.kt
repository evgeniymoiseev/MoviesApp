package com.example.moviesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.model.most_popular_movies.SimpleMovie

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: SimpleMovie)

    @Delete
    suspend fun delete(id: String)

    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<SimpleMovie>>
}