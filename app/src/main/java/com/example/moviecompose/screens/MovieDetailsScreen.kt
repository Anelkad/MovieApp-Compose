package com.example.moviecompose.screens

import IMAGE_URL
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
import com.example.moviecompose.models.Movie
import com.example.moviecompose.ui.theme.MovieComposeTheme
import com.example.moviecompose.utils.Resource

@Composable
fun MovieDetailsScreen(
    result: Resource<MovieDetails> = Resource.Loading,
    onBackPress: () -> Unit = {},
    movieOnSaveClick: (Movie) -> Unit = {}
){
    when (result){
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
                text = "Failure to load movies"
            )
        }
        is Resource.Success -> {
            MovieDetailsContent(
                movie = result.getSuccessResult(),
                onBackPress = onBackPress,
                movieOnSaveClick = movieOnSaveClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsContent(
    movie: MovieDetails,
    onBackPress: () -> Unit,
    movieOnSaveClick: (Movie) -> Unit
){
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = onBackPress
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
                model = IMAGE_URL.plus(movie.posterPath),
                placeholder = painterResource(R.drawable.loading_image),
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
                onClick = {movieOnSaveClick(movie.toMovie())},
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
                model = IMAGE_URL.plus(movie.backdropPath),
                placeholder = painterResource(R.drawable.loading_image),
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