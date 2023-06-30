package com.example.moviecompose.movieList.domain.repository

import androidx.paging.PagingData
import com.example.moviecompose.movieList.data.modelDTO.ListItem
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    fun getPagedMovieList(): Flow<PagingData<ListItem>>
}