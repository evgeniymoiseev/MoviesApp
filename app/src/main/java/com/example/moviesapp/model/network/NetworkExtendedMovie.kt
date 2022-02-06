package com.example.moviesapp.model.network

data class NetworkExtendedMovie(
    val actorList: List<Actor>,
    val awards: String,
    val boxOffice: BoxOffice,
    val companies: String,
    val companyList: List<Company>,
    val contentRating: String,
    val countries: String,
    val countryList: List<Country>,
    val directorList: List<Director>,
    val directors: String?,
    val errorMessage: String?,
    val fullCast: Any,
    val fullTitle: String,
    val genreList: List<Genre>?,
    val genres: String?,
    val id: String,
    val imDbRating: String?,
    val imDbRatingVotes: String,
    val image: String,
    val images: Any,
    val keywordList: List<String>,
    val keywords: String,
    val languageList: List<Language>,
    val languages: String,
    val metacriticRating: String,
    val originalTitle: String,
    val plot: String?,
    val plotLocal: String,
    val plotLocalIsRtl: Boolean,
    val posters: Any,
    val ratings: Any,
    val releaseDate: String,
    val runtimeMins: String,
    val runtimeStr: String,
    val similars: List<Similar>,
    val starList: List<Star>,
    val stars: String,
    val tagline: String,
    val title: String,
    val trailer: Any,
    val tvEpisodeInfo: Any,
    val tvSeriesInfo: Any,
    val type: String,
    val wikipedia: Any,
    val writerList: List<Writer>,
    val writers: String,
    val year: String?
) {

    data class Actor(
        val asCharacter: String,
        val id: String,
        val image: String,
        val name: String
    )

    data class BoxOffice(
        val budget: String,
        val cumulativeWorldwideGross: String,
        val grossUSA: String,
        val openingWeekendUSA: String
    )

    data class Company(
        val id: String,
        val name: String
    )

    data class Country(
        val key: String,
        val value: String
    )

    data class Director(
        val id: String,
        val name: String
    )

    data class Genre(
        val key: String,
        val value: String
    )

    data class Language(
        val key: String,
        val value: String
    )

    data class Similar(
        val directors: String,
        val fullTitle: String,
        val genres: String,
        val id: String,
        val imDbRating: String,
        val image: String,
        val plot: String,
        val stars: String,
        val title: String,
        val year: String
    )

    data class Star(
        val id: String,
        val name: String
    )

    data class Writer(
        val id: String,
        val name: String
    )
}