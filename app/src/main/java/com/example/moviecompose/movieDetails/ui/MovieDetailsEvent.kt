package com.example.moviecompose.movieDetails.ui

import com.example.moviecompose.models.Movie

sealed interface MovieDetailsEvent {
    data class LoadMovieDetails(val movieId: Int): MovieDetailsEvent
    object OnBackClick: MovieDetailsEvent
    data class OnSaveMovieClick(
        var movie: Movie
    ) : MovieDetailsEvent
}
