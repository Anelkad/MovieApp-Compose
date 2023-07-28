package com.example.moviecompose.movieList.ui.modelUI

import com.example.moviecompose.movieList.domain.model.Movie as Movie
import com.example.moviecompose.savedMovieList.domain.model.Movie as SavedMovie

data class MovieUI(
    val id: Int,
    val title: String,
    val overview: String?,
    val releaseDate: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Float?
){
    fun toDomainMovie(): Movie = Movie(
        id = id,
        title = title,
        overview = overview?:"",
        releaseDate = releaseDate?:"",
        posterPath = posterPath?:"",
        backdropPath = backdropPath?:"",
        voteAverage = voteAverage?:0F
    )

    fun toDomainSavedMovie(): SavedMovie = SavedMovie(
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
