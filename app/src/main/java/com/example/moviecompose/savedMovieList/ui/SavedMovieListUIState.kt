package com.example.moviecompose.savedMovieList.ui

import com.example.moviecompose.models.Movie
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieVideoResponse

sealed interface SavedMovieListUIState{
    object Loading: SavedMovieListUIState
    data class Data(val movieList: ArrayList<Movie>): SavedMovieListUIState
}
