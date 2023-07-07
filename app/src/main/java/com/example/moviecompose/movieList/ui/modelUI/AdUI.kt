package com.example.moviecompose.movieList.ui.modelUI

import com.example.moviecompose.movieList.domain.model.Ad

data class AdUI(
    val title: String,
    val description: String,
    val image: String
)

fun Ad.toUI(): AdUI = AdUI(
    title = title,
    description = description,
    image = image
)