package com.example.moviecompose.viewmodels

import androidx.lifecycle.*
import com.example.moviecompose.models.MovieListResponse
import com.example.moviecompose.repositories.MovieRepository
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movieListState = MutableLiveData<Resource<MovieListResponse>>(Resource.Loading)
    val movieListState: LiveData<Resource<MovieListResponse>> = _movieListState
    init {
        getMovieList(1)
    }
    fun getMovieList(page: Int) = viewModelScope.launch(Dispatchers.IO) {
        _movieListState.postValue(Resource.Loading)
        val result = repository.getMovieList(page)
        _movieListState.postValue(Resource.Success(result))
    }
}