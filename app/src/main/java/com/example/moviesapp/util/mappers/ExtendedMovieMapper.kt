package com.example.moviesapp.util.mappers

import com.example.moviesapp.model.ExtendedMovie
import com.example.moviesapp.model.NetworkExtendedMovie

class ExtendedMovieMapper : Mapper<NetworkExtendedMovie, ExtendedMovie> {
    override fun map(input: NetworkExtendedMovie): ExtendedMovie {
        val isEmptyRating = input.imDbRating.isEmpty()
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
            year = input.year,
            runtime = input.runtimeStr,
            poster = input.image,
            isEmptyRating = isEmptyRating,
            rating = input.imDbRating,
            directors = directorsStr,
            stars = starsStr,
            plot = input.plot
        )
    }
}