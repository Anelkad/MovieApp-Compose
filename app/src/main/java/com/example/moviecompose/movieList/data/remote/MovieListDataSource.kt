package com.example.moviecompose.movieList.data.remote

import com.example.moviecompose.movieList.data.modelDTO.MovieListResponseDTO
import javax.inject.Inject

class MovieListDataSource @Inject constructor(
    private val movieApiService: MovieListApiService
) {
    suspend fun getMovieList(page: Int): MovieListResponseDTO {
        return movieApiService.getMovieList(page)
    }
}