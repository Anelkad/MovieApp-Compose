package com.example.moviecompose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.repositories.MovieRepository
import com.example.moviecompose.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: MovieRepository
): ViewModel() {

    private val _movieDetailsDetailsState = MutableLiveData<Resource<MovieDetails>>(Resource.Loading)
    val movieDetailsDetailsState: LiveData<Resource<MovieDetails>> = _movieDetailsDetailsState

    fun getMovie(movieId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _movieDetailsDetailsState.postValue(Resource.Loading)
        val result = repository.getMovie(movieId)
        _movieDetailsDetailsState.postValue(Resource.Success(result))
    }

}