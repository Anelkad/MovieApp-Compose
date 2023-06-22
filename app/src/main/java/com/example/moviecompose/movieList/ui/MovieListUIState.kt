package com.example.moviecompose.movieList.ui

import androidx.paging.PagingData
import com.example.moviecompose.models.ListItem

sealed class MovieListUIState {

    object Loading: MovieListUIState()

    data class Error(var error: Exception): MovieListUIState()

    data class Success(
        val pagingMovieList: PagingData<ListItem>
    ): MovieListUIState()
}