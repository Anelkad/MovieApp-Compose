package com.example.moviecompose.movieDetails.domain.repository

import com.example.moviecompose.movieDetails.domain.model.MovieDetails
import com.example.moviecompose.movieDetails.domain.model.MovieVideoResponse

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    suspend fun getMovieVideo(movieId: Int): MovieVideoResponse
}