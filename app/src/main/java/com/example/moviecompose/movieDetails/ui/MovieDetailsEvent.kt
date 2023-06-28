package com.example.moviecompose.movieDetails.ui

import com.example.moviecompose.models.Movie

sealed interface MovieDetailsEvent {
    object OnBackClick: MovieDetailsEvent
    data class OnSaveMovieClick(
        var movie: Movie
    ) : MovieDetailsEvent
}
