package com.example.moviecompose.savedMovieList.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.movieList.domain.model.Movie
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMovieListViewModel @Inject constructor (
    private val savedMovieRepository: SavedMovieRepository
) : ViewModel() {

    private val initialState: SavedMovieListUIState by lazy {
        SavedMovieListUIState.Loading
    }

    private val _uiState: MutableStateFlow<SavedMovieListUIState> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _movieList: MutableStateFlow<ArrayList<Movie>> = MutableStateFlow(ArrayList())

    private val _event: MutableSharedFlow<SavedMovieListEvent> = MutableSharedFlow()
    //val event = _event.asSharedFlow()

    private val _effect: Channel<SavedMovieListEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private fun setState(newState: SavedMovieListUIState) {
        _uiState.tryEmit(newState)
        //_uiState.update { newState }
    }
    private fun setEffect(effectValue: SavedMovieListEffect) {
        viewModelScope.launch { _effect.send(effectValue) }
    }
    fun onEvent(event: SavedMovieListEvent) {
        when (event) {
            is SavedMovieListEvent.OnMovieClick ->
                setEffect (SavedMovieListEffect.NavigateToMovieDetails(event.movieId))
            is SavedMovieListEvent.OnDeleteMovieClick -> {
                setState(SavedMovieListUIState.Loading)
                deleteMovie(event.movieId)
            }
        }
    }

    init {
        getMovieList()
    }

     private fun getMovieList() = viewModelScope.launch {
       savedMovieRepository.getSavedMovieList().collect { resource ->
           if (resource is Resource.Success) {
               _movieList.value = resource.result
               Log.d("qwerty getMovieList", resource.result.size.toString())
               setState(
                   SavedMovieListUIState.Data(
                       movieList = _movieList.value
                   )
               )
           }
       }
    }

    private fun deleteMovie(movieId: Int) = viewModelScope.launch {
            //setEffect (SavedMovieListEffect.ShowWaitDialog)
            val result = savedMovieRepository.deleteMovie(movieId)
            when (result) {
                is Resource.Loading -> Unit
                is Resource.Success -> {
//                    setEffect(
//                        SavedMovieListEffect.ShowToast("Movie deleted!")
//                    )
                    setState(
                        SavedMovieListUIState.Data(
                            movieList = _movieList.value
                        )
                    )
                }
                is Resource.Failure ->
                    setEffect(
                        SavedMovieListEffect.ShowToast("Cannot delete movie!")
                    )
            }
        }
}
