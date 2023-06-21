package com.example.moviecompose.movieList.domain.repository

import androidx.paging.PagingData
import com.example.moviecompose.models.ListItem
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieListResponse
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(page: Int): MovieListResponse
    fun getPagedMovieList(): Flow<PagingData<ListItem>>
}