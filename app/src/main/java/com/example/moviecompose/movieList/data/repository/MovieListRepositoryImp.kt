package com.example.moviecompose.movieList.data.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecompose.movieList.domain.model.ListItem
import com.example.moviecompose.movieList.data.remote.MovieListDataSource
import com.example.moviecompose.movieList.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListRepositoryImp @Inject constructor(
    private val remoteDataSource: MovieListDataSource
): MovieListRepository {
    override fun getPagedMovieList(): Flow<PagingData<ListItem>> {
        return Pager(PagingConfig(pageSize = 10, initialLoadSize = 10)) {
            MoviePagingSource(remoteDataSource)
        }.flow
    }

}