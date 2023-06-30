package com.example.moviecompose.savedMovieList.ui

import com.example.moviecompose.movieList.domain.model.Movie

sealed interface SavedMovieListUIState{
    object Loading: SavedMovieListUIState
    data class Data(val movieList: ArrayList<Movie>): SavedMovieListUIState
}
