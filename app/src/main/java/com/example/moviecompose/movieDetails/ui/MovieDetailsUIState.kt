package com.example.moviecompose.movieDetails.ui

import com.example.moviecompose.movieDetails.data.modelDTO.MovieDetails
import com.example.moviecompose.movieDetails.data.modelDTO.MovieVideoResponse

sealed interface MovieDetailsUIState{
    object Loading: MovieDetailsUIState
    data class Data(
        val movie: MovieDetails,
        val videos: MovieVideoResponse
    ): MovieDetailsUIState
}
