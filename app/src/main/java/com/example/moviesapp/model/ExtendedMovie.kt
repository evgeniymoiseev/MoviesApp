package com.example.moviesapp.model

import androidx.annotation.DrawableRes

data class ExtendedMovie(
    val id: String,
    val title: String,
    val year: String,
    val runtime: String,
    val imageSrc: String,
    val isEmptyRating: Boolean,
    val rating: String,
    val directors: String,
    val stars: String,
    val plot: String,
    @DrawableRes val upDownDrawable: Int,
    val rank: String
)
