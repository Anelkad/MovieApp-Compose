package com.example.moviecompose.savedMovieList.ui
import com.example.moviecompose.models.Movie


sealed interface SavedMovieListEffect {
    object ShowWaitDialog: SavedMovieListEffect
    data class ShowToast(
        var text: String
        ): SavedMovieListEffect

    data class NavigateToMovieDetails(
        var movieId: Int
    ) : SavedMovieListEffect

}