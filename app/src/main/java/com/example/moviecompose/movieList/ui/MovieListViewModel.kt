package com.example.moviecompose.movieList.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.moviecompose.R
import com.example.moviecompose.models.ListItem
import com.example.moviecompose.movieList.domain.repository.MovieListRepository
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val savedMovieRepository: SavedMovieRepository
) : ViewModel(), EventHandler<MovieListEvent> {

    private var _movieListUiState: MutableStateFlow<MovieListUIState> =
        MutableStateFlow(MovieListUIState.Loading)
    var movieListUiState: StateFlow<MovieListUIState> = _movieListUiState.asStateFlow()

    //todo pagedMovieList перенести в state
//    val pagedMovieList: Flow<PagingData<ListItem>> =
//        movieListRepository.getPagedMovieList().cachedIn(viewModelScope)

    init {
        getMovieList()
    }

    private fun getMovieList() {
        viewModelScope.launch {
            _movieListUiState.emit(MovieListUIState.Loading)
            try {
                movieListRepository.getPagedMovieList()
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _movieListUiState.emit(MovieListUIState.Data(pagingData = pagingData))
                    }
            } catch (e: Exception) {
                _movieListUiState.emit(MovieListUIState.Error(e.message ?: e.localizedMessage))
            }
        }
    }

    override fun obtainEvent(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.OpenMovieDetails -> showMovieDetails(event)
            is MovieListEvent.SaveMovie -> saveMovie(event)
        }
    }


    private fun saveMovie(event: MovieListEvent.SaveMovie) {
        viewModelScope.launch {
            val result = savedMovieRepository.saveMovie(event.movie)
            if (result is Resource.Success)
                Toast.makeText(
                    event.context, "Movie \"${event.movie.title}\" saved!",
                    Toast.LENGTH_LONG
                ).show()
        }
    }

    private fun showMovieDetails(event: MovieListEvent.OpenMovieDetails) {
        event.navController.navigate(
            R.id.action_movieListFragment_to_movieDetailsFragment,
            Bundle().apply {
                putInt("id", event.movieId)
            }
        )
    }
}