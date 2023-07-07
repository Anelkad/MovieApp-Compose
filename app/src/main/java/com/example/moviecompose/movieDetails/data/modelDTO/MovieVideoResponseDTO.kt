package com.example.moviecompose.movieDetails.data.modelDTO

import com.example.moviecompose.movieDetails.domain.model.MovieVideoResponse

data class MovieVideoResponseDTO(
    val id: Int,
    val results: List<VideoDTO>
){
    fun toDomain(): MovieVideoResponse = MovieVideoResponse(
        id = id,
        results = results.map { it.toDomain() }
    )
}