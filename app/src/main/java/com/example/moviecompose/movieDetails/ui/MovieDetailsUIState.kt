package com.example.moviecompose.movieDetails.ui

import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieVideoResponse

sealed interface MovieDetailsUIState{
    object Loading: MovieDetailsUIState
    data class Data(
        val movie: MovieDetails,
        val videos: MovieVideoResponse
    ): MovieDetailsUIState
}
