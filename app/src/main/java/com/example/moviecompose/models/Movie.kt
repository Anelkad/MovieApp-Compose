package com.example.moviecompose.models

import com.google.gson.annotations.SerializedName

data class Movie(
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
)
