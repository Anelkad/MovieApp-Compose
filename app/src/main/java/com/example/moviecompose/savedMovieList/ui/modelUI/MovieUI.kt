package com.example.moviecompose.savedMovieList.ui.modelUI

import com.example.moviecompose.savedMovieList.domain.model.Movie

data class MovieUI(
    var id: Int,
    var title: String,
    var overview: String?,
    var releaseDate: String?,
    var posterPath: String?,
    var backdropPath: String?,
    var voteAverage: Float?
)

fun Movie.toUI(): MovieUI = MovieUI(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage
)

