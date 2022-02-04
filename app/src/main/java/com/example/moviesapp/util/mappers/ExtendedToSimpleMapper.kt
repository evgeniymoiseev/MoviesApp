package com.example.moviesapp.util.mappers

import com.example.moviesapp.model.ExtendedMovie
import com.example.moviesapp.model.SimpleMovie

class ExtendedToSimpleMapper(private val isFavorite: Boolean) : Mapper<ExtendedMovie, SimpleMovie> {
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