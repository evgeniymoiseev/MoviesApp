package com.example.moviesapp.di

import android.app.Application
import android.content.Context
import com.example.moviesapp.ui.fragments.detail.DetailMovieFragment
import com.example.moviesapp.ui.fragments.favorite.FavoriteFragment
import com.example.moviesapp.ui.fragments.most_popular.MostPopularFragment
import com.example.moviesapp.ui.fragments.search.SearchFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DataModule::class, FactoryModule::class])
interface AppComponent {

    fun inject(fragment: SearchFragment)
    fun inject(fragment: MostPopularFragment)
    fun inject(fragment: FavoriteFragment)
    fun inject(fragment: DetailMovieFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}