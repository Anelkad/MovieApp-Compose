package com.example.moviecompose.movieDetails.ui.modelUI

import com.example.moviecompose.movieDetails.domain.model.Video

data class VideoUI(
    val id: String,
    val iso_3166_1: String,
    val iso_639_1: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val published_at: String,
    val site: String,
    val size: Int,
    val type: String
)

fun Video.toUI(): VideoUI = VideoUI(
    id = id,
    iso_3166_1 = iso_3166_1,
    iso_639_1 = iso_639_1,
    key = key,
    name = name,
    official = official,
    published_at = published_at,
    site = site,
    size = size,
    type = type
)