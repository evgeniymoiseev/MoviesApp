package com.example.moviesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.model.local.SimpleMovie

@Database(
    entities = [SimpleMovie::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MovieDao

}