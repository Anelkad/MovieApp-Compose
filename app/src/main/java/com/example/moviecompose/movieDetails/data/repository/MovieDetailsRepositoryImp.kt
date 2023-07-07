package com.example.moviecompose.movieDetails.data.repository

import com.example.moviecompose.movieDetails.data.remote.MovieDetailsDataSource
import com.example.moviecompose.movieDetails.domain.model.MovieDetails
import com.example.moviecompose.movieDetails.domain.model.MovieVideoResponse
import com.example.moviecompose.movieDetails.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImp @Inject constructor(
    private val remoteDataSource: MovieDetailsDataSource
): MovieDetailsRepository {
    override suspend fun getMovieDetails(movieId: Int): MovieDetails = remoteDataSource.getMovieDetails(movieId).toDomain()
    override suspend fun getMovieVideo(movieId: Int): MovieVideoResponse = remoteDataSource.getMovieVideo(movieId).toDomain()
}