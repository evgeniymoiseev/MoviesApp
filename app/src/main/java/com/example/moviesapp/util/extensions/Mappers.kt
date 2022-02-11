package com.example.moviesapp.util.extensions

import com.example.moviesapp.R
import com.example.moviesapp.model.local.ExtendedMovie
import com.example.moviesapp.model.local.ShortMovie
import com.example.moviesapp.model.local.SimpleMovie
import com.example.moviesapp.model.network.NetworkExtendedMovie
import com.example.moviesapp.model.network.NetworkShortMovie
import com.example.moviesapp.model.network.NetworkSimpleMovie

fun ExtendedMovie.toSimpleMovie(isFavorite: Boolean): SimpleMovie {
    return SimpleMovie(
        id = id,
        imageSrc = imageSrc,
        upDownDrawable = upDownDrawable,
        rank = rank,
        title = title,
        year = year,
        rating = rating,
        isEmptyRating = isEmptyRating,
        isFavorite = isFavorite
    )
}

fun NetworkShortMovie.toShortMovie(): ShortMovie {
    return ShortMovie(
        id = id,
        image = image,
        title = title,
        description = description,
        isMovie = !description.contains("Series")
    )
}

fun NetworkSimpleMovie.toSimpleMovie(isInFavorites: Boolean): SimpleMovie {
    var rankInt = 0
    try {
        rankInt = rankUpDown.toInt()
    } catch (t: Throwable) {
        if (rankUpDown.contains(",")) rankInt =
            rankUpDown.replace(",", "").toInt()
    }
    val rankString = if (rankInt == 0) "-" else rankInt.toString()
    val rankDrawable = when {
        rankInt > 0 -> R.drawable.ic_arrow_up
        rankInt < 0 -> R.drawable.ic_arrow_down
        else -> R.drawable.ic_arrow_none
    }
    val isEmptyRating = imDbRating.isNullOrEmpty()

    return SimpleMovie(
        id = id,
        imageSrc = image,
        upDownDrawable = rankDrawable,
        rank = rankString,
        title = title,
        year = year,
        rating = imDbRating ?: "",
        isEmptyRating = isEmptyRating,
        isFavorite = isInFavorites
    )
}

fun NetworkExtendedMovie.toExtendedMovie(): ExtendedMovie {
    val isEmptyRating = imDbRating.isNullOrEmpty()
    val ratingStr = if (isEmptyRating) "" else imDbRating.toString()
    val directorsStr = buildString {
        directorList.forEach {
            append(it.name + "\n")
        }
    }
    val starsStr = buildString {
        starList.forEach {
            append(it.name + "\n")
        }
    }

    return ExtendedMovie(
        id = id,
        title = title,
        year = year ?: "",
        runtime = runtimeStr ?: "",
        imageSrc = image,
        isEmptyRating = isEmptyRating,
        rating = ratingStr,
        directors = directorsStr,
        stars = starsStr,
        plot = plot ?: "",
        upDownDrawable = R.drawable.ic_arrow_none,
        rank = "-"
    )
}