package com.example.moviecompose.savedMovieList.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.movieList.domain.model.Movie
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
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
    val event = _event.asSharedFlow()

    private val _effect: Channel<SavedMovieListEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private fun setState(reduce: SavedMovieListUIState.() -> SavedMovieListUIState) {
        val newState = uiState.value.reduce()
        _uiState.value = newState
    }

    fun setEvent(event: SavedMovieListEvent) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    private fun setEffect(effectValue: SavedMovieListEffect) {
        viewModelScope.launch { _effect.send(effectValue) }
    }
    fun onEvent(event: SavedMovieListEvent) {
        when (event) {
            is SavedMovieListEvent.ShowMovieList -> getMovieList()
            is SavedMovieListEvent.OnMovieClick ->
                setEffect (SavedMovieListEffect.NavigateToMovieDetails(event.movieId))
            is SavedMovieListEvent.OnDeleteMovieClick -> {
                deleteMovie(event.movieId)
            }
        }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collectLatest {
                onEvent(it)
            }
        }
    }

     private fun getMovieList() {
         viewModelScope.launch {
             savedMovieRepository.getSavedMovieList().collect { resource ->
                 when (resource) {
                     is Resource.Success -> {
                             setState {
                                     SavedMovieListUIState.Data(
                                         movieList = resource.result

                                 )
                             }
                         Log.d("qwerty getMovieList", resource.result.size.toString())
                     }

                     else -> {}
                 }
             }
         }
    }

    private fun deleteMovie(movieId: Int) = viewModelScope.launch{
            //setEffect (SavedMovieListEffect.ShowWaitDialog)
            savedMovieRepository.deleteMovie(movieId)
//            when (result) {
//                is Resource.Loading -> Unit
//                is Resource.Success -> {
////                    setEffect(
////                        SavedMovieListEffect.ShowToast("Movie deleted!")
////                    )
//                }
//                is Resource.Failure ->
//                    setEffect(
//                        SavedMovieListEffect.ShowToast("Cannot delete movie!")
//                    )
//            }
        }
}
