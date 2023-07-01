package com.example.moviecompose.savedMovieList.ui

import com.example.moviecompose.movieList.domain.model.Movie

data class State(
    val movieListState: SavedMovieListUIState
)
sealed interface SavedMovieListUIState{
    object Loading: SavedMovieListUIState
    data class Data(val movieList: List<Movie>): SavedMovieListUIState
}
