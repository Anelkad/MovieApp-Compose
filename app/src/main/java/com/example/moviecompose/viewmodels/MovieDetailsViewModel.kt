package com.example.moviecompose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.models.Movie
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.repositories.MovieRepository
import com.example.moviecompose.repository.SavedMovieRepository
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedMovieRepository: SavedMovieRepository
    ): ViewModel() {

    private val _movieDetailsDetailsState = MutableLiveData<Resource<MovieDetails>>(Resource.Loading)
    val movieDetailsDetailsState: LiveData<Resource<MovieDetails>> = _movieDetailsDetailsState

    private val _saveMovieState = Channel<Resource<Movie>?>()
    val saveMovieState: Flow<Resource<Movie>?> = _saveMovieState.receiveAsFlow()

    fun getMovie(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _movieDetailsDetailsState.postValue(Resource.Loading)
        val result = movieRepository.getMovie(movieId)
        _movieDetailsDetailsState.postValue(Resource.Success(result))
    }
    fun saveMovie(movie: Movie) = viewModelScope.launch {
        _saveMovieState.send(Resource.Loading)
        val result = savedMovieRepository.saveMovie(movie)
        _saveMovieState.send(result)
    }
}