package com.example.moviecompose.savedMovieList.data.modelDTO

import com.example.moviecompose.savedMovieList.domain.model.Movie

data class MovieDTO(
    var id: Int = 0,
    var title: String = "",
    var overview: String = "",
    var releaseDate: String = "",
    var posterPath: String = "",
    var backdropPath: String = "",
    var voteAverage: Float = 0F
){
    fun toDomain(): Movie = Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage
    )
}

