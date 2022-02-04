package com.example.moviesapp.util.mappers

import com.example.moviesapp.model.local.ExtendedMovie
import com.example.moviesapp.model.local.SimpleMovie

class LocalExtendedToLocalSimpleMapper(private val isFavorite: Boolean) : Mapper<ExtendedMovie, SimpleMovie> {
    override fun map(input: ExtendedMovie): SimpleMovie {
        return SimpleMovie(
            id = input.id,
            imageSrc = input.imageSrc,
            upDownDrawable = input.upDownDrawable,
            rank = input.rank,
            title = input.title,
            year = input.year,
            rating = input.rating,
            isEmptyRating = input.isEmptyRating,
            isFavorite = isFavorite
        )
    }
}