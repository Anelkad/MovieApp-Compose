package com.example.moviecompose.movieList.data.modelDTO

import com.example.moviecompose.movieList.domain.model.ListItem

sealed class ListItemDTO {

    data class MovieItemDTO(val movie: MovieDTO): ListItemDTO()
    data class AdItemDTO(val ad: AdDTO) : ListItemDTO()

    fun toDomain(): ListItem {
        return when (this){
            is MovieItemDTO -> ListItem.MovieItem(this.movie.toDomain())
            is AdItemDTO -> ListItem.AdItem(this.ad.toDomain())
        }
    }
}