package com.example.moviecompose

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moviecompose.models.Movie

@Composable
fun MovieListScreen(
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
    LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.padding(5.dp)
        ){
            items(movies.size){movieIndex ->
            MovieListItemCard(movies[movieIndex])
        }
    }
}