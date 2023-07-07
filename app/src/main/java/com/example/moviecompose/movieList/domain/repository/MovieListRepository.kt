package com.example.moviecompose.movieList.domain.repository

import androidx.paging.PagingData
import com.example.moviecompose.movieList.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    fun getPagedMovieList(): Flow<PagingData<ListItem>>
}