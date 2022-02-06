package com.example.moviesapp.util.mappers

import com.example.moviesapp.R
import com.example.moviesapp.model.local.ExtendedMovie
import com.example.moviesapp.model.network.NetworkExtendedMovie

class NetworkExtendedToLocalExtendedMapper : Mapper<NetworkExtendedMovie, ExtendedMovie> {
    override fun map(input: NetworkExtendedMovie): ExtendedMovie {
        val isEmptyRating = input.imDbRating.isNullOrEmpty()
        val ratingStr = if (isEmptyRating) "" else input.imDbRating.toString()
        val directorsStr = buildString {
            input.directorList.forEach {
                append(it.name + "\n")
            }
        }
        val starsStr = buildString {
            input.starList.forEach {
                append(it.name + "\n")
            }
        }

        return ExtendedMovie(
            id = input.id,
            title = input.title,
            year = input.year ?: "",
            runtime = input.runtimeStr,
            imageSrc = input.image,
            isEmptyRating = isEmptyRating,
            rating = ratingStr,
            directors = directorsStr,
            stars = starsStr,
            plot = input.plot ?: "",
            upDownDrawable = R.drawable.ic_arrow_none,
            rank = "-"
        )
    }
}