package com.example.moviecompose.di

import FIREBASE_URL
import com.example.moviecompose.apiClient.ApiClient
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //App lifecycle
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_URL)
    @Provides
    @Singleton
    fun provideService(): ApiClient = ApiClient()
}
