package com.example.moviecompose.movieList.ui


sealed interface MovieListEffect {
    object ShowWaitDialog: MovieListEffect
    data class ShowToast(
        var text: String
        ): MovieListEffect

    data class NavigateToMovieDetails(
        var movieId: Int
    ) : MovieListEffect

}