package com.example.moviecompose.movieList.data.modelDTO

import com.google.gson.annotations.SerializedName

data class MovieListResponseDTO(
    val page: Int,
    val results: List<MovieDTO>,
    @SerializedName("total_pages")
    val totalPages: Int
)
