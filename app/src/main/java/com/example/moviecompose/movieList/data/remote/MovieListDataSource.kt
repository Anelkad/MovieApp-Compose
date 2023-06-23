package com.example.moviecompose.movieList.data.remote

import com.example.moviecompose.models.MovieListResponse
import javax.inject.Inject

class MovieListDataSource @Inject constructor(
    private val movieApiService: MovieListApiService
) {
    suspend fun getMovieList(page: Int): MovieListResponse {
        return movieApiService.getMovieList(page)
    }
}