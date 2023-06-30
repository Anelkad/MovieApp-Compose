package com.example.moviecompose.movieList.data.modelDTO

import com.example.moviecompose.movieList.domain.model.Movie

sealed class ListItem {

    data class MovieItem(val movie: Movie): ListItem()
    data class AdItem(val ad: Ad) : ListItem()

}