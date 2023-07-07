package com.example.moviecompose.movieDetails.data.modelDTO

import com.example.moviecompose.movieDetails.domain.model.Genre

data class GenreDTO(
    val id: Int,
    val name: String
){
    fun toDomain(): Genre = Genre(
        id = id,
        name = name
    )
}