package com.example.moviecompose.di

import com.example.moviecompose.repositories.MovieRepository
import com.example.moviecompose.repositories.MovieRepositoryImp
import com.example.moviecompose.repository.SavedMovieRepository
import com.example.moviecompose.repository.SavedMovieRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelMovieModule {
    @Binds
    @ViewModelScoped //жизненный цикл ViewModelScope
    abstract fun bindsMovieRepository(imp: MovieRepositoryImp): MovieRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsSavedMovieRepository(imp: SavedMovieRepositoryImp): SavedMovieRepository
}