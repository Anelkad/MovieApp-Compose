package com.example.moviecompose.movieDetails.data.repository

import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.apiClient.ApiClient
import com.example.moviecompose.models.MovieVideoResponse
import com.example.moviecompose.movieDetails.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImp @Inject constructor(
    val service: ApiClient
): MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails = service.getMovieDetails(movieId)
    override suspend fun getMovieVideo(movieId: Int): MovieVideoResponse = service.getMovieVideo(movieId)
}