package com.example.moviesapp.model

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class SimpleMovie(
    @PrimaryKey val id: String,
    val imageSrc: String,
    @DrawableRes val upDownDrawable: Int,
    val rank: String,
    val title: String,
    val year: String,
    val rating: String,
    val isEmptyRating: Boolean,
    val isFavorite: Boolean,
)