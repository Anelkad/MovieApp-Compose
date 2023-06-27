package com.example.moviecompose.movieList.ui

import androidx.paging.PagingData
import com.example.moviecompose.models.ListItem

sealed class MovieListUIState {
    object Loading: MovieListUIState()
    data class Data(val pagingData: PagingData<ListItem>): MovieListUIState()
    data class Error(val message: String): MovieListUIState()
}

//data class MovieListUIState2(
//    val pagedMovieList: Flow<PagingData<ListItem>>? = null,
//    val isLoading: Boolean = false,
//    val error: String? = null
//)

//sealed class MovieListUIState {
//
//    object Loading: MovieListUIState()
//
//    // todo Error do effect
//    object Success: MovieListUIState()
//}