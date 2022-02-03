package com.example.moviesapp.util.mappers

import com.example.moviesapp.R
import com.example.moviesapp.model.most_popular_movies.MostPopularMovie
import com.example.moviesapp.model.most_popular_movies.SimpleMovie

class SimpleMovieMapper(private val isInFavorites: Boolean) :
    Mapper<MostPopularMovie, SimpleMovie> {
    override fun map(input: MostPopularMovie): SimpleMovie {
        var rankInt = 0
        try {
            rankInt = input.rankUpDown.toInt()
        } catch (t: Throwable) {
            if (input.rankUpDown.contains(",")) rankInt =
                input.rankUpDown.replace(",", "").toInt()
        }
        val rankString = if (rankInt == 0) "-" else rankInt.toString()
        val rankDrawable = when {
            rankInt > 0 -> R.drawable.ic_arrow_up
            rankInt < 0 -> R.drawable.ic_arrow_down
            else -> R.drawable.ic_arrow_none
        }
        val isEmptyRating = input.imDbRating.isEmpty()

        return SimpleMovie(
            id = input.id,
            imageSrc = input.image,
            upDownDrawable = rankDrawable,
            rank = rankString,
            title = input.title,
            year = input.year,
            rating = input.imDbRating,
            isEmptyRating = isEmptyRating,
            isFavorite = isInFavorites
        )
    }
}