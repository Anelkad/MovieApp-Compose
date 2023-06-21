package com.example.moviecompose.movieList

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviecompose.movieList.domain.repository.MovieListRepository
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.models.ListItem
import com.example.moviecompose.models.Movie
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val savedMovieRepository: SavedMovieRepository
) : ViewModel() {

    private val _saveMovieState = Channel<Resource<Movie>>()
    val saveMovieState: Flow<Resource<Movie>?> = _saveMovieState.receiveAsFlow()

    val pagedMovieList: Flow<PagingData<ListItem>> =
        movieListRepository.getPagedMovieList().cachedIn(viewModelScope)

    fun saveMovie(movie: Movie) = viewModelScope.launch {
        _saveMovieState.send(Resource.Loading)
        val result = savedMovieRepository.saveMovie(movie)
        _saveMovieState.send(result)
    }
}