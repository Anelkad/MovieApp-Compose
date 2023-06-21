package com.example.moviecompose.apiClient

import BASE_URL
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieListResponse
import com.example.moviecompose.models.MovieVideoResponse
import com.example.moviecompose.movieDetails.data.apiService.MovieDetailsService
import com.example.moviecompose.movieList.data.apiService.MovieListService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
//todo разделить apiClient для movieListService и movieDetailsService
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val movieListService: MovieListService = retrofit.create(MovieListService::class.java)
    private val movieDetailsService: MovieDetailsService = retrofit.create(MovieDetailsService::class.java)
    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    suspend fun getMovieDetails(id: Int): MovieDetails {
        return movieDetailsService.getMovie(id)
    }

    suspend fun getMovieVideo(id: Int): MovieVideoResponse{
        return movieDetailsService.getMovieVideo(id)
    }

    suspend fun getMovieList(page: Int): MovieListResponse {
        return movieListService.getMovieList(page)
    }
}

