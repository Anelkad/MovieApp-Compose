package com.example.moviecompose.savedMovieList.domain.repository

import com.example.moviecompose.savedMovieList.data.modelDTO.MovieDTO
import com.example.moviecompose.savedMovieList.domain.model.Movie
import com.example.moviecompose.utils.Resource
import kotlinx.coroutines.flow.Flow

interface SavedMovieRepository {
    fun getSavedMovieList(): Flow<Resource<List<MovieDTO>>>
    suspend fun deleteMovie(movieId: Int): Resource<Int>
    suspend fun saveMovie(movie: Movie): Resource<Movie>
}