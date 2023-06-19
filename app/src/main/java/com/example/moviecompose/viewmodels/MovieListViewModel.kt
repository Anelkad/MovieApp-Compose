package com.example.moviecompose.viewmodels

import androidx.lifecycle.*
import com.example.moviecompose.models.Movie
import com.example.moviecompose.models.MovieListResponse
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
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val savedMovieRepository: SavedMovieRepository
) : ViewModel() {

    private val _movieListState = MutableLiveData<Resource<MovieListResponse>>(Resource.Loading)
    val movieListState: LiveData<Resource<MovieListResponse>> = _movieListState

    private val _saveMovieState = Channel<Resource<Movie>>()
    val saveMovieState: Flow<Resource<Movie>?> = _saveMovieState.receiveAsFlow()

    init {
        getMovieList(1)
    }
    fun getMovieList(page: Int) = viewModelScope.launch(Dispatchers.IO) {
        _movieListState.postValue(Resource.Loading)
        val result = movieRepository.getMovieList(page)
        _movieListState.postValue(Resource.Success(result))
    }
    fun saveMovie(movie: Movie) = viewModelScope.launch {
        _saveMovieState.send(Resource.Loading)
        val result = savedMovieRepository.saveMovie(movie)
        _saveMovieState.send(result)
    }
}