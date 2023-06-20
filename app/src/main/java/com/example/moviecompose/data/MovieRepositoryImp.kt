package com.example.moviecompose.data
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecompose.models.ListItem
import com.example.moviecompose.models.MovieDetails
import com.example.okhttp.api.RetrofitService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    val service: RetrofitService
): MovieRepository {
    override suspend fun getMovie(movieId: Int): MovieDetails = service.getMovie(movieId)
    override suspend fun getMovieList(page: Int) = service.getMovieList(page)
    override fun getPagedMovieList(): Flow<PagingData<ListItem>> {
        return Pager(PagingConfig(pageSize = 10, initialLoadSize = 10)) {
            MoviePagingSource(service)
        }.flow
    }

}