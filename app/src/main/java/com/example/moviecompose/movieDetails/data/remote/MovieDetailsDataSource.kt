package com.example.moviecompose.movieDetails.data.remote

import com.example.moviecompose.movieDetails.data.modelDTO.MovieDetailsDTO
import com.example.moviecompose.movieDetails.data.modelDTO.MovieVideoResponseDTO
import javax.inject.Inject

class MovieDetailsDataSource @Inject constructor(
    private val movieApiService: MovieDetailsApiService
) {
    suspend fun getMovieDetails(id: Int): MovieDetailsDTO {
        return movieApiService.getMovie(id)
    }

    suspend fun getMovieVideo(id: Int): MovieVideoResponseDTO {
        return movieApiService.getMovieVideo(id)
    }
}