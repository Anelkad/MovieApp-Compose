package com.example.moviecompose.movieList.ui.modelUI

import com.example.moviecompose.movieList.domain.model.Movie

data class MovieUI(
    val id: Int,
    val title: String,
    val overview: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Float?
){
    fun toDomain(): Movie = Movie(
        id = id,
        title = title,
        overview = overview?:"",
        releaseDate = releaseDate?:"",
        posterPath = posterPath?:"",
        backdropPath = backdropPath?:"",
        voteAverage = voteAverage?:0F
    )
}

fun Movie.toUI(): MovieUI = MovieUI(
    id = id,
    title = title,
    overview = overview?:"",
    releaseDate = releaseDate,
    posterPath = posterPath?:"",
    backdropPath = backdropPath?:"",
    voteAverage = voteAverage?:0F
)
