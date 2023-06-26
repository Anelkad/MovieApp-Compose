package com.example.moviecompose.savedMovieList.data.remote

import com.example.moviecompose.models.Movie
import javax.inject.Inject

class SavedMovieDataSource @Inject constructor(
    val service: SavedMovieService
) {
     fun getSavedMovieList() = service.getSavedMovieList()
     suspend fun deleteMovie(movieId: Int) = service.deleteMovie(movieId)
     suspend fun saveMovie(movie: Movie) = service.saveMovie(movie)
}