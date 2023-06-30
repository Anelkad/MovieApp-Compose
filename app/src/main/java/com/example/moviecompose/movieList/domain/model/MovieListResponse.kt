package com.example.moviecompose.movieList.domain.model

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    val page: Int,
    val results: ArrayList<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int
)
