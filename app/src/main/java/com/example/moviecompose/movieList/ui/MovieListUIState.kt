package com.example.moviecompose.movieList.ui

sealed class MovieListUIState {

    object Loading: MovieListUIState()

    //todo Error do effect
    object Success: MovieListUIState()
}