package com.example.moviesapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.model.local.SimpleMovie

@Dao
interface MovieDao {

    @Insert
    suspend fun insert(movie: SimpleMovie)

    @Query("DELETE FROM movies WHERE id=:id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM movies WHERE id=:id")
    fun getMovieById(id: String): LiveData<SimpleMovie>

    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<SimpleMovie>>
}