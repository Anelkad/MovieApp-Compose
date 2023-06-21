package com.example.moviecompose.movieList.data.repository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecompose.models.ListItem
import com.example.moviecompose.apiClient.ApiClient
import com.example.moviecompose.movieList.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListRepositoryImp @Inject constructor(
    val service: ApiClient
): MovieListRepository {
    //todo data source => service ?
    override suspend fun getMovieList(page: Int) = service.getMovieList(page)
    override fun getPagedMovieList(): Flow<PagingData<ListItem>> {
        return Pager(PagingConfig(pageSize = 10, initialLoadSize = 10)) {
            MoviePagingSource(service)
        }.flow
    }

}