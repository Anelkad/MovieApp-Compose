package com.example.moviecompose.movieList.ui

import androidx.navigation.NavController
import com.example.moviecompose.models.Movie

sealed class MovieListEvent {
    object Initial: MovieListEvent()
    data class OnMovieClick(
        var movieId: Int,
        var navController: NavController
    ) : MovieListEvent()

    data class OnSaveMovieClick(
        var movie: Movie
    ) : MovieListEvent()
}
