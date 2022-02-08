package com.example.moviesapp

import android.app.Application
import com.example.moviesapp.di.AppComponent
import com.example.moviesapp.di.DaggerAppComponent
import timber.log.Timber

class MovieApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .application(this)
            .build()
    }
}