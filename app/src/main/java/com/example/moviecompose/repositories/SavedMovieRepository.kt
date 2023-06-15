package com.example.moviecompose.repository

import com.example.moviecompose.models.Movie
import com.example.moviecompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SavedMovieRepository {
    fun getSavedMovieList(): Flow<Resource<ArrayList<Movie>>>
    suspend fun deleteMovie(movieId: Int): Resource<Int>
    suspend fun saveMovie(movie: Movie): Resource<Movie>
}