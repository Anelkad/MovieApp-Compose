package com.example.moviecompose.savedMovieList.ui.modelUI

sealed interface SavedMovieListEvent {
    object ShowMovieList: SavedMovieListEvent
    data class OnMovieClick(
        var movieId: Int
    ) : SavedMovieListEvent

    data class OnDeleteMovieClick(
        var movieId: Int
    ) : SavedMovieListEvent
}
