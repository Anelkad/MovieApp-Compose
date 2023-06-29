package com.example.moviecompose.savedMovieList.ui

import com.example.moviecompose.models.Movie

sealed interface SavedMovieListEvent {
    data class OnMovieClick(
        var movieId: Int
    ) : SavedMovieListEvent

    data class OnDeleteMovieClick(
        var movieId: Int
    ) : SavedMovieListEvent
}
