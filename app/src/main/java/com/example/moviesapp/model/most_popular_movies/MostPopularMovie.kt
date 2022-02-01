package com.example.moviesapp.model.most_popular_movies

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MostPopularMovie(
    val crew: String,
    val fullTitle: String,
    val id: String,
    val imDbRating: String,
    val imDbRatingCount: String,
    val image: String,
    val rank: String,
    val rankUpDown: String,
    val title: String,
    val year: String
) : Parcelable