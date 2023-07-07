package com.example.moviecompose.movieDetails.ui.modelUI

import com.example.moviecompose.movieDetails.domain.model.Genre

data class GenreUI(
    val id: Int,
    val name: String
)

fun Genre.toUI(): GenreUI = GenreUI(
    id = id,
    name = name
)