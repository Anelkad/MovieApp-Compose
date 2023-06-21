package com.example.moviecompose.di

import com.example.moviecompose.data.*
import com.example.moviecompose.movieDetails.data.repository.MovieDetailsRepositoryImp
import com.example.moviecompose.movieDetails.domain.repository.MovieDetailsRepository
import com.example.moviecompose.movieList.data.repository.MovieListRepositoryImp
import com.example.moviecompose.movieList.domain.repository.MovieListRepository
import com.example.moviecompose.savedMovieList.data.repository.SavedMovieRepositoryImp
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
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
    abstract fun bindsMovieListRepository(imp: MovieListRepositoryImp): MovieListRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsMovieDetailsRepository(imp: MovieDetailsRepositoryImp): MovieDetailsRepository

    @Binds
    @ViewModelScoped
    abstract fun bindsSavedMovieRepository(imp: SavedMovieRepositoryImp): SavedMovieRepository
}