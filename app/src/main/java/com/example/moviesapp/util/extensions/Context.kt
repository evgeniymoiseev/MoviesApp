package com.example.moviesapp.util.extensions

import android.content.Context
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is MovieApplication -> appComponent
        else -> this.applicationContext.appComponent
    }
