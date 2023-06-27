package com.example.moviecompose.movieList.ui


sealed class MovieListEffect {
    object ShowWaitDialog: MovieListEffect()
    data class ShowToast(
        var text: String
        ): MovieListEffect()
}