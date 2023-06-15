package com.example.moviecompose.repositories
import com.example.moviecompose.models.MovieDetails
import com.example.okhttp.api.RetrofitService
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    val service: RetrofitService
): MovieRepository {
    override suspend fun getMovie(movieId: Int): MovieDetails = service.getMovie(movieId)
    override suspend fun getMovieList(page: Int) = service.getMovieList(page)

}