package com.example.moviecompose.savedMovieList.ui

import com.example.moviecompose.models.Movie
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieVideoResponse

data class SavedMovieListUIState(
    val isLoading: Boolean,
    val movieList: ArrayList<Movie>
)
