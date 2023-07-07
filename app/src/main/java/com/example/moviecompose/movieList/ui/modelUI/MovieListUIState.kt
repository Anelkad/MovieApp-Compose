package com.example.moviecompose.movieList.ui.modelUI

import androidx.paging.PagingData
import com.example.moviecompose.movieList.domain.model.ListItem

data class MovieListUIState(
    val pagingData: PagingData<ListItemUI>,
    val isLoading: Boolean = true
)

//todo uiState 4 states: loading, data, error, empty
