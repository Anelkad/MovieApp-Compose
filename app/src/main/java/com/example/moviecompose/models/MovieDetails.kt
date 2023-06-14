package com.example.moviecompose.models

import com.google.gson.annotations.SerializedName


data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    val tagline: String,
    val revenue: Int,
    val runtime: Int
){
    fun toMovie(): Movie =
        Movie(id, title, overview, releaseDate, posterPath, backdropPath, voteAverage)
}
