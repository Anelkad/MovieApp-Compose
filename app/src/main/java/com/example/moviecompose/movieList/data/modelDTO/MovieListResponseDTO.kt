package com.example.moviecompose.movieList.data.modelDTO

import com.example.moviecompose.movieList.domain.model.Movie
import com.example.moviecompose.movieList.domain.model.MovieListResponse
import com.google.gson.annotations.SerializedName

data class MovieListResponseDTO(
    val page: Int,
    val results: List<Movie>, //todo wtf with movie
    @SerializedName("total_pages")
    val totalPages: Int
){
    fun toDomain(): MovieListResponse = MovieListResponse(
        page = page,
        results = results,
        totalPages = totalPages
    )
}
