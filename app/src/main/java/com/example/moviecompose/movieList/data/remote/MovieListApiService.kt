package com.example.moviecompose.movieList.data.remote

import API_KEY
import LANGUAGE
import com.example.moviecompose.movieList.data.modelDTO.MovieListResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieListApiService {
    @GET("discover/movie")
    suspend fun getMovieList(
        @Query("page")
        page: Int = 1,
        @Query("api_key")
        api_key: String = API_KEY,
        @Query("with_genres")
        with_genres: String = "16,18",
        @Query("language")
        language: String = LANGUAGE
    ): MovieListResponseDTO
}