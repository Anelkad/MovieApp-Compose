package com.example.moviecompose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviecompose.models.Movie

@Composable
fun MovieList(
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            content = {
                items(items = movies){ movie->
                    MovieListItemCard(movie = movie)
                }
            },
            modifier = Modifier.padding(5.dp)
        )
}