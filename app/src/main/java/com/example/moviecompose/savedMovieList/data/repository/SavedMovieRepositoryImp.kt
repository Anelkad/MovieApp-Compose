package com.example.moviecompose.savedMovieList.data.repository

import com.example.moviecompose.movieList.domain.model.Movie
import com.example.moviecompose.savedMovieList.data.remote.SavedMovieDataSource
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import javax.inject.Inject

class SavedMovieRepositoryImp @Inject constructor(
    val dataSource: SavedMovieDataSource
): SavedMovieRepository {
    override fun getSavedMovieList() = dataSource.getSavedMovieList()
    override suspend fun deleteMovie(movieId: Int) = dataSource.deleteMovie(movieId)
    override suspend fun saveMovie(movie: Movie) = dataSource.saveMovie(movie)
}