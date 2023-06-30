package com.example.moviecompose.savedMovieList.ui

sealed interface SavedMovieListEvent {
    data class OnMovieClick(
        var movieId: Int
    ) : SavedMovieListEvent

    data class OnDeleteMovieClick(
        var movieId: Int
    ) : SavedMovieListEvent
}
