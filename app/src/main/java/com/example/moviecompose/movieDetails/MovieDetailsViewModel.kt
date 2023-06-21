package com.example.moviecompose.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.movieDetails.domain.repository.MovieDetailsRepository
import com.example.moviecompose.savedMovieList.domain.repository.SavedMovieRepository
import com.example.moviecompose.models.Movie
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.MovieVideoResponse
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
    private val movieDetailsRepository: MovieDetailsRepository,
    private val savedMovieRepository: SavedMovieRepository
    ): ViewModel() {

    private val _movieDetailsDetailsState = MutableLiveData<Resource<MovieDetails>>(Resource.Loading)
    val movieDetailsDetailsState: LiveData<Resource<MovieDetails>> = _movieDetailsDetailsState

    private val _movieVideoState = MutableLiveData<Resource<MovieVideoResponse>>(Resource.Loading)
    val movieVideoState: LiveData<Resource<MovieVideoResponse>> = _movieVideoState

    private val _saveMovieState = Channel<Resource<Movie>?>()
    val saveMovieState: Flow<Resource<Movie>?> = _saveMovieState.receiveAsFlow()

    fun getMovieDetails(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _movieDetailsDetailsState.postValue(Resource.Loading)
        val movieDetailsResult = movieDetailsRepository.getMovieDetails(movieId)
        _movieDetailsDetailsState.postValue(Resource.Success(movieDetailsResult))

        _movieVideoState.postValue(Resource.Loading)
        val videoResult = movieDetailsRepository.getMovieVideo(movieId)
        _movieVideoState.postValue(Resource.Success(videoResult))
    }
    fun saveMovie(movie: Movie) = viewModelScope.launch {
        _saveMovieState.send(Resource.Loading)
        val result = savedMovieRepository.saveMovie(movie)
        _saveMovieState.send(result)
    }
}