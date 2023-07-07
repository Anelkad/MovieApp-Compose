package com.example.moviecompose.savedMovieList.domain.model
data class Movie(
    var id: Int,
    var title: String,
    var overview: String?,
    var releaseDate: String?,
    var posterPath: String?,
    var backdropPath: String?,
    var voteAverage: Float?
)

