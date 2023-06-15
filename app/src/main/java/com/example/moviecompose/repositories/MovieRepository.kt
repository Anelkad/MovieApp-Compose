package com.example.moviecompose.repositories

import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieListResponse

interface MovieRepository {
    suspend fun getMovie(movieId: Int): MovieDetails
    suspend fun getMovieList(page: Int): MovieListResponse
}