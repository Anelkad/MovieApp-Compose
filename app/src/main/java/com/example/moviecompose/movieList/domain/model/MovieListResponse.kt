package com.example.moviecompose.movieList.domain.model

data class MovieListResponse(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int
)