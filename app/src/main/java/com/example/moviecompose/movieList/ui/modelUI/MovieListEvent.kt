package com.example.moviecompose.movieList.ui.modelUI


sealed interface MovieListEvent {
    object StopLoading: MovieListEvent
    data class OnMovieClick(
        var movieId: Int
    ) : MovieListEvent

    data class OnSaveMovieClick(
        var movie: MovieUI
    ) : MovieListEvent
}
