package com.example.moviecompose.movieDetails.domain.model

data class MovieDetails(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val voteAverage: Float,
    val voteCount: Int,
    val productionCountries: List<ProductionCountry>,
    val genres: List<Genre>,
    val tagline: String,
    val revenue: Int,
    val runtime: Int
)
