package com.example.moviecompose.movieList.data.modelDTO

import com.example.moviecompose.movieList.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDTO(
    var id: Int,
    var title: String,
    var overview: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    @SerializedName("poster_path")
    var posterPath: String?,
    @SerializedName("backdrop_path")
    var backdropPath: String?,
    @SerializedName("vote_average")
    var voteAverage: Float?
){
    fun toDomain(): Movie =
        Movie(
            id = id,
            title = title,
            overview = overview?:"",
            releaseDate = releaseDate?:"",
            posterPath = posterPath?:"",
            backdropPath = backdropPath?:"",
            voteAverage = voteAverage?:0F
        )
}
