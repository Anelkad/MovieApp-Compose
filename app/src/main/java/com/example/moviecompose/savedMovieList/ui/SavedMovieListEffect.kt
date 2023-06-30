package com.example.moviecompose.savedMovieList.ui


sealed interface SavedMovieListEffect {
    object ShowWaitDialog: SavedMovieListEffect
    data class ShowToast(
        var text: String
        ): SavedMovieListEffect

    data class NavigateToMovieDetails(
        var movieId: Int
    ) : SavedMovieListEffect

}