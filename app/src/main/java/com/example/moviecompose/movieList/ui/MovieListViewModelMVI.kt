package com.example.moviecompose.movieList.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviecompose.R
import com.example.moviecompose.models.ListItem
import com.example.moviecompose.movieList.domain.repository.MovieListRepository
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModelMVI @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val savedMovieRepository: SavedMovieRepository
) : ViewModel(), EventHandler<MovieListEvent> {

    private var _movieListUiState: MutableLiveData<MovieListUIState> =
        MutableLiveData(MovieListUIState.Loading)
    var movieListUiState: LiveData<MovieListUIState> = _movieListUiState

    val pagedMovieList: Flow<PagingData<ListItem>> =
        movieListRepository.getPagedMovieList().cachedIn(viewModelScope)

    override fun obtainEvent(event: MovieListEvent) {
        when (event) {
//            MovieListEvent.EnterMovieListScreen -> fetchMovieList()
            is MovieListEvent.ShowMovieDetails -> showMovieDetails(event)
            is MovieListEvent.SaveMovie -> saveMovie(event)
            MovieListEvent.StartLoading -> {
                _movieListUiState.postValue(MovieListUIState.Loading)
            }
            MovieListEvent.StopLoading -> {
                _movieListUiState.postValue(MovieListUIState.Success)
            }
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

    private fun showMovieDetails(event: MovieListEvent.ShowMovieDetails) {
        event.navController.navigate(
            R.id.action_movieListFragment_to_movieDetailsFragment,
            Bundle().apply {
                putInt("id", event.movieId)
            }
        )
    }
}