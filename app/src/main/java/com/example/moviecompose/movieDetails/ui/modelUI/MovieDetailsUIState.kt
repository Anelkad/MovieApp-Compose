package com.example.moviecompose.movieDetails.ui.modelUI


sealed interface MovieDetailsUIState{
    object Loading: MovieDetailsUIState
    data class Data(
        val movie: MovieDetailsUI,
        val videos: MovieVideoResponseUI
    ): MovieDetailsUIState
}
