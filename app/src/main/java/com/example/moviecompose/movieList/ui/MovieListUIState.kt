package com.example.moviecompose.movieList.ui

import androidx.paging.PagingData
import com.example.moviecompose.models.ListItem
import kotlinx.coroutines.flow.Flow


data class MovieListUIState(
    val pagedMovieList: Flow<PagingData<ListItem>>? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

//sealed class MovieListUIState {
//
//    object Loading: MovieListUIState()
//
//    // todo Error do effect
//    object Success: MovieListUIState()
//}