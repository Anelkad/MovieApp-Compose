package com.example.moviecompose.movieDetails.ui.modelUI

import com.example.moviecompose.movieDetails.domain.model.ProductionCountry

data class ProductionCountryUI(
    val iso_3166_1: String,
    val name: String
)

fun ProductionCountry.toUI(): ProductionCountryUI = ProductionCountryUI(
    iso_3166_1 = iso_3166_1,
    name = name
)