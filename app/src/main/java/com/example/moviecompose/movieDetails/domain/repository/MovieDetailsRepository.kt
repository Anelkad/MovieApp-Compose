package com.example.moviecompose.movieDetails.domain.repository

import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieVideoResponse

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMovieVideo(movieId: Int): MovieVideoResponse
}