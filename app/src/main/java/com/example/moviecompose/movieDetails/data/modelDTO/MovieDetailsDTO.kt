package com.example.moviecompose.movieDetails.data.modelDTO

import com.example.moviecompose.movieDetails.domain.model.MovieDetails
import com.google.gson.annotations.SerializedName

data class MovieDetailsDTO(
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
    val productionCountries: List<ProductionCountryDTO>,
    val genres: List<GenreDTO>,
    val tagline: String,
    val revenue: Int,
    val runtime: Int
){
    fun toDomain(): MovieDetails = MovieDetails(
        id = id,
        title = title,
        originalTitle = originalTitle,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        voteCount = voteCount,
        productionCountries = productionCountries.map { it.toDomain() },
        genres = genres.map { it.toDomain() },
        tagline = tagline,
        revenue = revenue,
        runtime = runtime
    )
}
