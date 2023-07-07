package com.example.moviecompose.savedMovieList.ui.compose

import IMAGE_URL
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviecompose.R
import com.example.moviecompose.savedMovieList.ui.modelUI.MovieUI
import com.example.moviecompose.ui.theme.MovieComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedMovieListItemCard(
    movie: MovieUI,
    movieOnClick: (Int) -> Unit,
    movieOnDeleteClick: (Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = {movieOnClick(movie.id)},
        modifier = Modifier
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = IMAGE_URL.plus(movie.posterPath),
                placeholder = painterResource(R.drawable.loading_image),
                contentDescription = null,
                modifier = Modifier
                    .padding(10.dp)
                    .width(100.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .weight(1f)
            ) {
                Text(
                    text = movie.title,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Премьера: ".plus(movie.releaseDate),
                    fontSize = 19.sp,
                    fontStyle = FontStyle.Italic
                )
            }
                IconButton(
                    onClick = {movieOnDeleteClick(movie.id)},
                    modifier = Modifier
                        .size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_delete_forever_24),
                        contentDescription = "Delete Movie",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                    )
                }
           }
    }
}

@Preview(showBackground = true)
@Composable
fun SavedMovieListItemPreview() {
    MovieComposeTheme {
        //SavedMovieListScreen()
    }
}