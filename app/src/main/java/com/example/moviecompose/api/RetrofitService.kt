package com.example.okhttp.api

import BASE_URL
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(getClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val movieAPI: MovieApi = retrofit.create(MovieApi::class.java)

    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        return client
    }

    suspend fun getMovie(id: Int): MovieDetails {
        return movieAPI.getMovie(id)
    }

    suspend fun getMovieList(page: Int): MovieListResponse {
        return movieAPI.getMovieList(page)
    }
}

