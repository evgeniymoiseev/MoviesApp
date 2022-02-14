package com.example.moviesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.moviesapp.api.MoviesApi
import com.example.moviesapp.db.MovieDatabase
import com.example.moviesapp.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            MovieDatabase::class.java,
            "movie_db.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(database: MovieDatabase, api: MoviesApi): MovieRepository {
        return MovieRepository(db = database, api = api)
    }
}