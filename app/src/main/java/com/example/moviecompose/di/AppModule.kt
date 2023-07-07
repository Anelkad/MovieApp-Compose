package com.example.moviecompose.di

import BASE_URL
import FIREBASE_URL
import com.example.moviecompose.movieDetails.data.remote.MovieDetailsApiService
import com.example.moviecompose.movieList.data.remote.MovieListApiService
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //App lifecycle
object AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }//todo handle exceptions on network connection
    @Provides
    @Singleton
    fun provideMovieListService(retrofit: Retrofit): MovieListApiService =
        retrofit.create(MovieListApiService::class.java)

    @Provides
    @Singleton
    fun provideMovieDetailsService(retrofit: Retrofit): MovieDetailsApiService =
        retrofit.create(MovieDetailsApiService::class.java)

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_URL)

}
