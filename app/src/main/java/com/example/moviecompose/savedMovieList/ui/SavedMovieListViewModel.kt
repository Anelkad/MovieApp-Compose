package com.example.moviecompose.savedMovieList.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.models.Movie
import com.example.moviecompose.movieList.ui.MovieListEffect
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
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
        SavedMovieListUIState(isLoading = true, movieList = ArrayList())
    }

    private val _uiState: MutableStateFlow<SavedMovieListUIState> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<SavedMovieListEvent> = MutableSharedFlow()
    //val event = _event.asSharedFlow()

    private val _effect: Channel<SavedMovieListEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private fun setState(newState: SavedMovieListUIState) {
        _uiState.value = newState
    }
    private fun setEffect(effectValue: SavedMovieListEffect) {
        viewModelScope.launch { _effect.send(effectValue) }
    }
    fun onEvent(event: SavedMovieListEvent) {
        when (event) {
            is SavedMovieListEvent.OnMovieClick ->
                setEffect (SavedMovieListEffect.NavigateToMovieDetails(event.movieId))
            is SavedMovieListEvent.OnDeleteMovieClick -> {
                deleteMovie(event.movieId)
            }
        }
    }

    init {
        getMovieList()
    }

    private fun getMovieList() = viewModelScope.launch {
       savedMovieRepository.getSavedMovieList().collect{ resource ->
           if (resource is Resource.Success) {
               Log.d("qwerty", resource.result.size.toString())
               setState(
                   SavedMovieListUIState(
                       isLoading = false,
                       movieList = resource.result
                   )
               )
           }
       }
    }

    private fun deleteMovie(movieId: Int) = viewModelScope.launch {
        viewModelScope.launch {
            setEffect (SavedMovieListEffect.ShowWaitDialog)
            val result = savedMovieRepository.deleteMovie(movieId)
            when (result){
                is Resource.Loading -> Unit
                is Resource.Success -> {
                    setEffect(
                        SavedMovieListEffect.ShowToast("Movie deleted!")
                    )
                }
                is Resource.Failure ->
                    setEffect (
                        SavedMovieListEffect.ShowToast("Cannot delete movie!")
                    )
            }
        }
    }
}
