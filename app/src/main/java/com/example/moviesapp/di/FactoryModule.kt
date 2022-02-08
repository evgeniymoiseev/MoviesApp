package com.example.moviesapp.di

import android.app.Application
import com.example.moviesapp.repository.MovieRepository
import com.example.moviesapp.ui.fragments.favorite.FavoriteViewModelFactory
import com.example.moviesapp.ui.fragments.most_popular.MostPopularViewModelFactory
import com.example.moviesapp.ui.fragments.search.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class FactoryModule {

    @Provides
    fun provideMostPopularFactory(
        application: Application,
        repository: MovieRepository
    ): MostPopularViewModelFactory {
        return MostPopularViewModelFactory(application = application, repository = repository)
    }

    @Provides
    fun provideSearchFactory(
        application: Application,
        repository: MovieRepository
    ): SearchViewModelFactory {
        return SearchViewModelFactory(application = application, repository = repository)
    }

    @Provides
    fun provideFavoriteFactory(
        repository: MovieRepository
    ): FavoriteViewModelFactory {
        return FavoriteViewModelFactory(repository = repository)
    }

}