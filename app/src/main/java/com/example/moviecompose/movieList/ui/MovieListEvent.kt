package com.example.moviecompose.movieList.ui

import android.content.Context
import androidx.navigation.NavController
import com.example.moviecompose.models.Movie

sealed class MovieListEvent {
    data class OpenMovieDetails(
        var movieId: Int,
        var navController: NavController
    ) : MovieListEvent()

    data class SaveMovie(
        var movie: Movie,
        var context: Context
    ) : MovieListEvent()
}