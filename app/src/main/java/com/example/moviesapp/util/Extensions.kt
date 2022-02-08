package com.example.moviesapp.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import com.example.moviesapp.MovieApplication
import com.example.moviesapp.di.AppComponent

val Context.appComponent: AppComponent
    get() = when (this) {
        is MovieApplication -> appComponent
        else -> this.applicationContext.appComponent
    }

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}
