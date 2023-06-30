package com.example.moviecompose.movieList.data.modelDTO

import com.example.moviecompose.movieList.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDTO(
    var id: Int = 0,
    var title: String = "",
    var overview: String = "",
    @SerializedName("release_date")
    var releaseDate: String = "",
    @SerializedName("poster_path")
    var posterPath: String = "",
    @SerializedName("backdrop_path")
    var backdropPath: String = "",
    @SerializedName("vote_average")
    var voteAverage: Float = 0F
){
    fun toDomain() = Movie(
        id,
        title,
        overview,
        releaseDate,
        posterPath,
        backdropPath,
        voteAverage
    )
}
