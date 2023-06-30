package com.example.moviecompose.movieDetails.data.remote

import com.example.moviecompose.movieDetails.data.modelDTO.MovieDetails
import com.example.moviecompose.movieDetails.data.modelDTO.MovieVideoResponse
import javax.inject.Inject

class MovieDetailsDataSource @Inject constructor(
    private val movieApiService: MovieDetailsApiService
) {
    suspend fun getMovieDetails(id: Int): MovieDetails {
        return movieApiService.getMovie(id)
    }

    suspend fun getMovieVideo(id: Int): MovieVideoResponse {
        return movieApiService.getMovieVideo(id)
    }
}