package com.example.moviecompose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.models.Movie
import com.example.moviecompose.utils.Resource
import com.example.moviecompose.repository.SavedMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMovieListViewModel @Inject constructor (
    private val repository: SavedMovieRepository
) : ViewModel() {

    private val _savedMovieList = MutableLiveData<Resource<ArrayList<Movie>>>(Resource.Loading)
    val savedMovieList: LiveData<Resource<ArrayList<Movie>>> =_savedMovieList

    private val _deleteMovieState = Channel<Resource<Int>>()
    val deleteMovieState: Flow<Resource<Int>?> = _deleteMovieState.receiveAsFlow()

    init {
        getMovieList()
    }

    fun getMovieList() = viewModelScope.launch {
       repository.getSavedMovieList().collect{
            _savedMovieList.value = it
       }
    }

    fun deleteMovie(movieId: Int) = viewModelScope.launch {
        _deleteMovieState.send(Resource.Loading)
        val result = repository.deleteMovie(movieId)
        _deleteMovieState.send(result)
    }
}
