package com.example.moviecompose.movieDetails.ui.modelUI

import com.example.moviecompose.movieDetails.domain.model.MovieVideoResponse

data class MovieVideoResponseUI(
    val id: Int,
    val results: List<VideoUI>
)

fun MovieVideoResponse.toUI(): MovieVideoResponseUI = MovieVideoResponseUI(
    id = id,
    results = results.map { it.toUI() }
)