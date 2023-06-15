package com.example.moviecompose.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecompose.models.Movie
import com.example.moviecompose.utils.Resource
import com.example.moviecompose.repository.SavedMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedMovieListViewModel @Inject constructor (
    private val repository: SavedMovieRepository
) : ViewModel() {

    private val _savedMovieList = MutableLiveData<Resource<ArrayList<Movie>>>(Resource.Loading)
    val savedMovieList: LiveData<Resource<ArrayList<Movie>>> =_savedMovieList

    private val _saveMovieState = MutableLiveData<Resource<Movie>?>(null)
    val saveMovieState: LiveData<Resource<Movie>?> = _saveMovieState

    private val _deleteMovieState = MutableLiveData<Resource<Int>?>(null)
    val deleteMovieState: LiveData<Resource<Int>?> = _deleteMovieState

    init {
        getMovieList()
    }

    fun getMovieList() = viewModelScope.launch {
       repository.getSavedMovieList().collect{
            _savedMovieList.value = it
       }
    }

    fun deleteMovie(movieId: Int) = viewModelScope.launch {
        _deleteMovieState.value = Resource.Loading
        val result = repository.deleteMovie(movieId)
        _deleteMovieState.value = result
    }

    fun saveMovie(movie: Movie) = viewModelScope.launch {
        _saveMovieState.value = Resource.Loading
        val result = repository.saveMovie(movie)
        _saveMovieState.value = result
    }
    fun clearSaveMovieState(){
        _saveMovieState.value = null
    }

    fun clearDeleteMovieState(){
        _deleteMovieState.value = null
    }
}
