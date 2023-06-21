package com.example.moviecompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviecompose.models.Movie
import com.example.moviecompose.screens.SavedMovieListItemCard
import com.example.moviecompose.utils.Resource

@Composable
fun SavedMovieListScreen(
    result: Resource<ArrayList<Movie>> = Resource.Loading,
    movieOnClick: (Int) -> Unit = {},
    movieOnDeleteClick: (Int) -> Unit = {}
){
    when (result){
        is Resource.Success -> {
            val movies = result.getSuccessResult()
            LazyColumn(
                modifier = Modifier.padding(5.dp)
            ){
                items(items = movies){ movie->
                    SavedMovieListItemCard(
                        movie = movie,
                        movieOnClick = movieOnClick,
                        movieOnDeleteClick = movieOnDeleteClick
                    )
                }
            }
        }
        is Resource.Loading -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                CircularProgressIndicator()
            }
        }
        is Resource.Failure -> {
            Text(
                text = "Failure to load saved movies"
            )
        }
    }
}