package com.example.moviecompose.movieDetails.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.movieDetails.domain.repository.MovieDetailsRepository
import com.example.moviecompose.movieDetails.ui.modelUI.MovieDetailsEffect
import com.example.moviecompose.movieDetails.ui.modelUI.MovieDetailsEvent
import com.example.moviecompose.movieDetails.ui.modelUI.MovieDetailsUIState
import com.example.moviecompose.movieDetails.ui.modelUI.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
    ): ViewModel() {

    private val initialState: MovieDetailsUIState by lazy {
        MovieDetailsUIState.Loading
    }

    private val _uiState: MutableStateFlow<MovieDetailsUIState> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<MovieDetailsEvent> = MutableSharedFlow()
    //val event = _event.asSharedFlow()

    private val _effect: Channel<MovieDetailsEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private fun setState(newState: MovieDetailsUIState) {
        _uiState.value = newState
    }
    private fun setEffect(effectValue: MovieDetailsEffect) {
        viewModelScope.launch { _effect.send(effectValue) }
    }
    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.LoadMovieDetails -> getMovieDetails(event.movieId)
            is MovieDetailsEvent.OnBackClick ->
                setEffect (MovieDetailsEffect.NavigateBack)
        }
    }
    private fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val movieDetails = movieDetailsRepository.getMovieDetails(movieId)
                val videos = movieDetailsRepository.getMovieVideo(movieId)

                setState(
                    MovieDetailsUIState.Data(
                        movie = movieDetails.toUI(),
                        videos = videos.toUI()
                    )
                )

            } catch (e: Exception) {
                setEffect (MovieDetailsEffect.ShowToast("Something went wrong. Reason: ${e.message ?: e.localizedMessage}"))
            }
        }
    }

}