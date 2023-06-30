package com.example.moviecompose.movieDetails.data.repository

import com.example.moviecompose.movieDetails.data.modelDTO.MovieDetails
import com.example.moviecompose.movieDetails.data.modelDTO.MovieVideoResponse
import com.example.moviecompose.movieDetails.data.remote.MovieDetailsDataSource
import com.example.moviecompose.movieDetails.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImp @Inject constructor(
    private val remoteDataSource: MovieDetailsDataSource
): MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails = remoteDataSource.getMovieDetails(movieId)
    override suspend fun getMovieVideo(movieId: Int): MovieVideoResponse = remoteDataSource.getMovieVideo(movieId)
}