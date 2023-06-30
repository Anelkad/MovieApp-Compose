package com.example.moviecompose.movieList.domain.model

data class Movie(
    var id: Int = 0,
    var title: String = "",
    var overview: String = "",
    var releaseDate: String = "",
    var posterPath: String = "",
    var backdropPath: String = "",
    var voteAverage: Float = 0F
)
