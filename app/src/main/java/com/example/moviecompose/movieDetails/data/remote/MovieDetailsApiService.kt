package com.example.moviecompose.movieDetails.data.remote

import API_KEY
import LANGUAGE
import com.example.moviecompose.movieDetails.data.modelDTO.MovieDetailsDTO
import com.example.moviecompose.movieDetails.data.modelDTO.MovieVideoResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsApiService {
    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id")
        movie_id: Int,
        @Query("api_key")
        api_key: String = API_KEY,
        @Query("language")
        language: String = LANGUAGE
    ): MovieDetailsDTO

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideo(
        @Path("movie_id")
        movie_id: Int,
        @Query("api_key")
        api_key: String = API_KEY
    ): MovieVideoResponseDTO
}