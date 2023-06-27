package com.example.moviecompose.movieList.ui

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.moviecompose.R
import com.example.moviecompose.movieList.domain.repository.MovieListRepository
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val savedMovieRepository: SavedMovieRepository
) : BaseViewModel <MovieListEvent, MovieListUIState, MovieListEffect>() {
    override fun createInitialState(): MovieListUIState {
        return MovieListUIState.Loading
    }

    override fun createInitialEvent(): MovieListEvent {
        return MovieListEvent.Initial
    }
    override fun handleEvent(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.Initial -> getMovieList()
            is MovieListEvent.OnMovieClick -> showMovieDetails(event)
            is MovieListEvent.OnSaveMovieClick -> saveMovie(event)
        }
    }

//    val pagedMovieList: Flow<PagingData<ListItem>> =
//        movieListRepository.getPagedMovieList().cachedIn(viewModelScope)

    private fun getMovieList() {
        viewModelScope.launch {
            setState { MovieListUIState.Loading }
            try {
                movieListRepository.getPagedMovieList()
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        setState { MovieListUIState.Data(pagingData = pagingData)}
                    }
            } catch (e: Exception) {
                setState { MovieListUIState.Error(e.message ?: e.localizedMessage)}
            }
        }
    }


    private fun saveMovie(event: MovieListEvent.OnSaveMovieClick) {
        viewModelScope.launch {
            setEffect { MovieListEffect.ShowWaitDialog }
            val result = savedMovieRepository.saveMovie(event.movie)
            when (result){
                is Resource.Success ->
                    setEffect {
                        MovieListEffect.ShowToast("Movie \"${result.result.title}\" saved!")
                    }
                is Resource.Failure ->
                    setEffect {
                        MovieListEffect.ShowToast("Cannot save movie!")
                    }
                else -> {}
            }
        }
    }

    private fun showMovieDetails(event: MovieListEvent.OnMovieClick) {
        event.navController.navigate(
            R.id.action_movieListFragment_to_movieDetailsFragment,
            Bundle().apply {
                putInt("id", event.movieId)
            }
        )
    }
}