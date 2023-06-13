package com.example.moviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviecompose.models.Movie
import com.example.moviecompose.ui.theme.MovieComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieComposeTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MovieItemContent(movie: Movie) {
    Column(
            modifier = Modifier
                .padding(5.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(5.dp)

        ) {
            AsyncImage(
                model = movie.posterPath,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(10.dp)),
            )
            Text(
                text = movie.title,
                fontSize = 30.sp
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = movie.voteAverage.toString(),
                    fontSize = 24.sp,
                )
                IconButton(
                    onClick = {},
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_bookmark_border_24),
                        contentDescription = "Add Movie",
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
}

@Composable
fun MyApp(
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
    Surface{
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            content = {
                items(items = movies){ movie->
                    MovieItemContent(movie = movie)
                }
        },
            modifier = Modifier.padding(5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieComposeTheme {
        MyApp()
    }
}