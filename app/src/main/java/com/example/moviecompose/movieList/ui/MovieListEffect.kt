package com.example.moviecompose.movieList.ui

import androidx.navigation.NavController
import com.example.moviecompose.models.Movie


sealed class MovieListEffect {
    object ShowWaitDialog: MovieListEffect()
    data class ShowToast(
        var text: String
        ): MovieListEffect()

//    data class NavigateToMovieDetails(
//        var movieId: Int
//    ) : MovieListEffect()

//    data class SaveMovie(
//        var movie: Movie
//    ) : MovieListEffect()
}