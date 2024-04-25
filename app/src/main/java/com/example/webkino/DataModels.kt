package com.example.webkino

import androidx.compose.ui.graphics.ImageBitmap

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Movie(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Long,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    var poster_image: ImageBitmap?
)

data class Genre(
    val id: Int,
    val name: String,
    var isChecked: Boolean = true
)

data class SortingMethod(
    val id: String,
    val name: String,
    var isSelected: Boolean = false
)

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val vote_average: Double,
    val poster_path: String?,
    val genres: List<Genre>,
    val homepage: String?,
    val imdb_id: String?,
    val production_countries: List<ProductionCountry>,
    val status: String,
    val tagline: String?,
    var poster_image: ImageBitmap?
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)