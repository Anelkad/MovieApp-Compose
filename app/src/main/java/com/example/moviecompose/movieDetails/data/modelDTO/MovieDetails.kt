package com.example.moviecompose.movieDetails.data.modelDTO

import com.example.moviecompose.movieList.domain.model.Movie
import com.google.gson.annotations.SerializedName

//todo DTO
data class MovieDetails(
    val id: Int,
    val title: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("vote_average")
    val voteAverage: Float,
    @SerializedName("vote_count")
    val voteCount: Int,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,
    val genres: List<Genre>,
    val tagline: String,
    val revenue: Int,
    val runtime: Int
){
    fun toMovie(): Movie =
        Movie(id, title, overview, releaseDate, posterPath, backdropPath, voteAverage)
}
