package com.example.moviecompose.movieList.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviecompose.movieList.data.modelDTO.Ad
import com.example.moviecompose.movieList.data.modelDTO.ListItem
import com.example.moviecompose.movieList.data.remote.MovieListDataSource

class MoviePagingSource(
    private val dataSource: MovieListDataSource
) : PagingSource<Int, ListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {

        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = dataSource.getMovieList(nextPage)

            val ad = Ad("1Fit",
                "Абонемент на все виды спорта",
                "https://resources.cdn-kaspi.kz/shop/medias/sys_master/images/images/h4b/hf7/47592727773214/1fit-bezlimit-3-mesaca-101420202-1-Container.png"
            )

            val list = buildList{
                addAll(movieListResponse.results.map { ListItem.MovieItem(it.toDomain())} )
                //каждые 10 фильмов - реклама
                add(10, ListItem.AdItem(ad))
                add(21, ListItem.AdItem(ad))
            }

            LoadResult.Page(
                data = list,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (nextPage < movieListResponse.totalPages)
                    movieListResponse.page.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int? {
        return 1
    }
}
