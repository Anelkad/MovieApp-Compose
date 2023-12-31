package com.example.moviecompose.movieList.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView

@Composable
fun MovieListScreen(
    recyclerView: RecyclerView,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                recyclerView
            }
        )
    }
}