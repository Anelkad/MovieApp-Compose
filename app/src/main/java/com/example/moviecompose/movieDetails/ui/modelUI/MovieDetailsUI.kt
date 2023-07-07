package com.example.moviecompose.movieDetails.ui.modelUI

import com.example.moviecompose.movieDetails.domain.model.MovieDetails

data class MovieDetailsUI(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Float,
    val voteCount: Int,
    val productionCountries: List<ProductionCountryUI>,
    val genres: List<GenreUI>,
    val tagline: String,
    val revenue: Int,
    val runtime: Int
)

fun MovieDetails.toUI(): MovieDetailsUI = MovieDetailsUI(
    id = id,
    title = title,
    originalTitle = originalTitle,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
    productionCountries = productionCountries.map { it.toUI() },
    genres = genres.map { it.toUI() },
    tagline = tagline,
    revenue = revenue,
    runtime = runtime
)
