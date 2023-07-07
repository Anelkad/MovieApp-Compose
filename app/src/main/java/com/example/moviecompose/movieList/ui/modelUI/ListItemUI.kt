package com.example.moviecompose.movieList.ui.modelUI

import com.example.moviecompose.movieList.domain.model.ListItem

sealed class ListItemUI {
    data class MovieItemUI(val movie: MovieUI): ListItemUI()
    data class AdItemUI(val ad: AdUI) : ListItemUI()
}

fun ListItem.toUI(): ListItemUI {
    return when (this){
        is ListItem.MovieItem -> ListItemUI.MovieItemUI(this.movie.toUI())
        is ListItem.AdItem -> ListItemUI.AdItemUI(this.ad.toUI())
    }
}