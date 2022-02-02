package com.example.moviesapp.util.mappers

import com.example.moviesapp.R
import com.example.moviesapp.model.most_popular_movies.MostPopularMovie
import com.example.moviesapp.model.most_popular_movies.SimpleMovie
import timber.log.Timber

class SimpleMovieMapper : Mapper<MostPopularMovie, SimpleMovie> {
    override fun map(source: MostPopularMovie): SimpleMovie {
        var rankInt = 0
        try {
            rankInt = source.rankUpDown.toInt()
        } catch (t: Throwable) {
            if (source.rankUpDown.contains(",")) rankInt =
                source.rankUpDown.replace(",", "").toInt()
        }
        val rankString = if (rankInt == 0) "-" else rankInt.toString()
        val rankDrawable = when {
            rankInt > 0 -> R.drawable.ic_arrow_up
            rankInt < 0 -> R.drawable.ic_arrow_down
            else -> R.drawable.ic_arrow_none
        }
        val isEmptyRating = source.imDbRating.isEmpty()

        return SimpleMovie(
            id = source.id,
            imageSrc = source.image,
            upDownDrawable = rankDrawable,
            rank = rankInt.toString(),
            title = source.title,
            year = source.year,
            rating = source.imDbRating,
            isEmptyRating = isEmptyRating
        )
    }
}