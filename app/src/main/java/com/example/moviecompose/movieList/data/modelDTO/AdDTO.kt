package com.example.moviecompose.movieList.data.modelDTO

import com.example.moviecompose.movieList.domain.model.Ad

data class AdDTO(
    val title: String,
    val description: String,
    val image: String
){
    fun toDomain(): Ad = Ad(
        title = title,
        description = description,
        image = image
    )
}