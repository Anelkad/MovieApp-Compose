package com.example.moviecompose.movieList.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviecompose.models.Movie
import com.example.moviecompose.movieList.domain.repository.MovieListRepository
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val savedMovieRepository: SavedMovieRepository
) : ViewModel() {

    private val initialState: MovieListUIState by lazy {
        MovieListUIState(pagingData = PagingData.empty())
    }

    private val _uiState: MutableStateFlow<MovieListUIState> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<MovieListEvent> = MutableSharedFlow()
    //val event = _event.asSharedFlow()

    private val _effect: Channel<MovieListEffect> = Channel()
    val effect = _effect.receiveAsFlow()

     private fun setState(newState: MovieListUIState) {
         //todo или сделать viewModelScope.launch { _uiState.emit(newState) }
         _uiState.value = newState
    }
     private fun setEffect(effectValue: MovieListEffect) {
        viewModelScope.launch { _effect.send(effectValue) }
    }
     fun onEvent(event: MovieListEvent) {
        when (event) {
            is MovieListEvent.OnMovieClick ->
                setEffect (MovieListEffect.NavigateToMovieDetails(event.movieId) )
            is MovieListEvent.OnSaveMovieClick -> {
                saveMovie(event.movie)
            }
        }
    }
    init {
        getMovieList()
    }
    private fun getMovieList() {
        viewModelScope.launch {
            try {
                movieListRepository.getPagedMovieList()
                    .cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        setState (MovieListUIState(pagingData = pagingData))
                    }
            } catch (e: Exception) {
                setEffect (MovieListEffect.ShowToast("Something went wrong. Reason: ${e.message ?: e.localizedMessage}"))
            }
        }
    }

     private fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            setEffect (MovieListEffect.ShowWaitDialog)
            val result = savedMovieRepository.saveMovie(movie)
            when (result){
                is Resource.Loading -> Unit
                is Resource.Success ->
                    setEffect (
                        MovieListEffect.ShowToast("Movie \"${result.result.title}\" saved!")
                    )
                is Resource.Failure ->
                    setEffect (
                        MovieListEffect.ShowToast("Cannot save movie!")
                    )
            }
        }
    }
}