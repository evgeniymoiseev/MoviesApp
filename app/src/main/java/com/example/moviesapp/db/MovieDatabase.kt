package com.example.moviesapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moviesapp.model.local.SimpleMovie

@Database(
    entities = [SimpleMovie::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie_db.db"
        ).build()
    }
}