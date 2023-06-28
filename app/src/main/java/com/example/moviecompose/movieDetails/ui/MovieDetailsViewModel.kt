package com.example.moviecompose.movieDetails.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.movieDetails.domain.repository.MovieDetailsRepository
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.models.Movie
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository,
    private val savedMovieRepository: SavedMovieRepository
    ): ViewModel() {

    private val initialState: MovieDetailsUIState by lazy {
        MovieDetailsUIState.Loading
    }

    private val _uiState: MutableState<MovieDetailsUIState> = mutableStateOf(initialState)
    val uiState: State<MovieDetailsUIState> = _uiState

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
            is MovieDetailsEvent.OnBackClick ->
                setEffect (MovieDetailsEffect.NavigateBack)
            is MovieDetailsEvent.OnSaveMovieClick -> {
                saveMovie(event.movie)
            }
        }
    }
    fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            try {
                val movieDetails = movieDetailsRepository.getMovieDetails(movieId)
                val videos = movieDetailsRepository.getMovieVideo(movieId)

                setState(
                    MovieDetailsUIState.Data(
                        movie = movieDetails,
                        videos = videos
                    )
                )

            } catch (e: Exception) {
                setEffect (MovieDetailsEffect.ShowToast("Something went wrong. Reason: ${e.message ?: e.localizedMessage}"))
            }
        }
    }

    private fun saveMovie(movie: Movie) {
        viewModelScope.launch {
            setEffect (MovieDetailsEffect.ShowWaitDialog)
            val result = savedMovieRepository.saveMovie(movie)
            when (result){
                is Resource.Loading -> Unit
                is Resource.Success ->
                    setEffect (
                        MovieDetailsEffect.ShowToast("Movie \"${result.result.title}\" saved!")
                    )
                is Resource.Failure ->
                    setEffect (
                        MovieDetailsEffect.ShowToast("Cannot save movie!")
                    )
            }
        }
    }

}