package com.example.okhttp.api

import API_KEY
import LANGUAGE
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
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
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id")
        movie_id: Int,
        @Query("api_key")
        api_key: String = API_KEY,
        @Query("language")
        language: String = LANGUAGE
    ): MovieDetails
}