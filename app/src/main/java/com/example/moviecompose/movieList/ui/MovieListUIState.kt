package com.example.moviecompose.movieList.ui

import androidx.paging.PagingData
import com.example.moviecompose.movieList.data.modelDTO.ListItem

data class MovieListUIState(
    val pagingData: PagingData<ListItem>,
    val isLoading: Boolean = true
)

//todo uiState 4 states: loading, data, error, empty
