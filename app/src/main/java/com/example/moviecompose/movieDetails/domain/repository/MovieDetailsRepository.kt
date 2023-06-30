package com.example.moviecompose.movieDetails.domain.repository

import com.example.moviecompose.movieDetails.data.modelDTO.MovieDetails
import com.example.moviecompose.movieDetails.data.modelDTO.MovieVideoResponse

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMovieVideo(movieId: Int): MovieVideoResponse
}