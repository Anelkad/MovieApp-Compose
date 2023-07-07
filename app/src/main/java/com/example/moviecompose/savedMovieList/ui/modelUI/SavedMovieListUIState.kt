package com.example.moviecompose.savedMovieList.ui.modelUI

sealed interface SavedMovieListUIState{
    object Loading: SavedMovieListUIState
    data class Data(val movieList: List<MovieUI>): SavedMovieListUIState
}
