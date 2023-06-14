package com.example.moviecompose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviecompose.models.Movie

@Composable
fun SavedMovieListScreen(
    movies: List<Movie> = List(100){
        Movie(1,
            "Kino",
            "asdf",
            "12-12-2012",
            "https://lumiere-a.akamaihd.net/v1/images/p_thelittlemermaid_2023_final_796_94759fcc.jpeg",
            "",
            5.0F
        )
    }
){
        LazyColumn(
            modifier = Modifier.padding(5.dp)
        ){
            items(items = movies){ movie->
                SavedMovieListItemCard(movie = movie)
            }
       }
}