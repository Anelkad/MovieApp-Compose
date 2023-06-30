package com.example.moviecompose.savedMovieList.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moviecompose.models.Movie
import com.example.moviecompose.savedMovieList.ui.compose.SavedMovieListItemCard
import com.example.moviecompose.utils.Resource

@Composable
fun SavedMovieListScreen(
    movieList: ArrayList<Movie>,
    movieOnClick: (Int) -> Unit = {},
    movieOnDeleteClick: (Int) -> Unit = {}
){
    if (movieList.isEmpty())
        Text(
            text = "No saved movies",
            textAlign = TextAlign.Center
        )
    else {
        LazyColumn(
            modifier = Modifier.padding(5.dp)
        ) {
            items(items = movieList) { movie ->
                SavedMovieListItemCard(
                    movie = movie,
                    movieOnClick = movieOnClick,
                    movieOnDeleteClick = movieOnDeleteClick
                )
            }
        }
    }
}