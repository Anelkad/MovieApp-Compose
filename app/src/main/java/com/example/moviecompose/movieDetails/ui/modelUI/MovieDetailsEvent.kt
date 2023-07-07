package com.example.moviecompose.movieDetails.ui.modelUI

sealed interface MovieDetailsEvent {
    data class LoadMovieDetails(val movieId: Int): MovieDetailsEvent
    object OnBackClick: MovieDetailsEvent
}
