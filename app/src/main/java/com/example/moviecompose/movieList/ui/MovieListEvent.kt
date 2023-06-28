package com.example.moviecompose.movieList.ui

import com.example.moviecompose.models.Movie

sealed interface MovieListEvent {
    object StopLoading: MovieListEvent
    data class OnMovieClick(
        var movieId: Int
    ) : MovieListEvent

    data class OnSaveMovieClick(
        var movie: Movie
    ) : MovieListEvent
}
