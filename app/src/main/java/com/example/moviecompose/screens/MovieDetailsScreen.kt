package com.example.moviecompose.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviecompose.models.MovieDetails
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviecompose.R
import com.example.moviecompose.ui.theme.MovieComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreen(
    movie: MovieDetails = MovieDetails(1,
        "Kino",
        "asdfa".repeat(100),
        "12-12-2012",
        "https://lumiere-a.akamaihd.net/v1/images/p_thelittlemermaid_2023_final_796_94759fcc.jpeg",
        "https://lumiere-a.akamaihd.net/v1/images/p_thelittlemermaid_2023_final_796_94759fcc.jpeg",
        5.0F,
        "Hello world!",
        12000000,
        120
    )
){
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = movie.title,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .verticalScroll(rememberScrollState())
        ){
            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = "Премьера: ".plus(movie.releaseDate),
                fontSize = 20.sp
            )
            AsyncImage(
                model = movie.posterPath,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .fillMaxWidth()
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = "${movie.runtime/60} ч ${movie.runtime%60} мин",
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = "Кассовые сборы: ${movie.revenue/1000000} млн $",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.End),
                shape = RectangleShape
            ){
                Icon(
                    painter = painterResource(R.drawable.baseline_bookmark_border_24),
                    contentDescription = "Favorite",
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Text(
                    text = " Save movie",
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = movie.overview,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            AsyncImage(
                    model = movie.backdropPath,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .padding(vertical = 5.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsPreview() {
    MovieComposeTheme {
        MovieDetailsScreen()
    }
}