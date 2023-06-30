package com.example.moviecompose.savedMovieList.domain.repository

import com.example.moviecompose.movieList.domain.model.Movie
import com.example.moviecompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SavedMovieRepository {
    fun getSavedMovieList(): Flow<Resource<ArrayList<Movie>>>
    suspend fun deleteMovie(movieId: Int): Resource<Int>
    suspend fun saveMovie(movie: Movie): Resource<Movie>
}