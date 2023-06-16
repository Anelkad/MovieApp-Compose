package com.example.moviecompose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecompose.models.Movie
import com.example.moviecompose.models.MovieListResponse
import com.example.moviecompose.utils.Resource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListScreen(
    result: Resource<MovieListResponse> = Resource.Loading,
    movieOnClick: (Int) -> Unit = {},
    movieOnSaveClick: (Movie) -> Unit = {},
    recyclerView: RecyclerView,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                recyclerView
            }
        )
        when (result) {
            is Resource.Success -> {
                val movies = result.getSuccessResult().results
//            LazyVerticalStaggeredGrid(
//                columns = StaggeredGridCells.Fixed(2),
//                modifier = Modifier.padding(5.dp)
//            ) {
//                items(movies.size) { movieIndex ->
//                    MovieListItemCard(
//                        movie = movies[movieIndex],
//                        movieOnClick = movieOnClick,
//                        movieOnSaveClick = movieOnSaveClick
//                    )
//                }
//            }
            }
            is Resource.Loading -> {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    CircularProgressIndicator()
//                }
            }
            is Resource.Failure -> {
                Text(
                    text = "Failure to load movies"
                )
            }
        }
    }
}