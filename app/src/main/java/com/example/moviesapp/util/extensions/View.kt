package com.example.moviesapp.util.extensions

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.hide() {
    visibility = View.GONE
}

fun ProgressBar.show() {
    visibility = View.VISIBLE
}