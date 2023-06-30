package com.example.moviecompose.movieList.ui.compose

import IMAGE_URL
import androidx.compose.foundation.border
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
import com.example.moviecompose.movieList.domain.model.Movie
import com.example.moviecompose.ui.theme.MovieComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListItemCard(
    movie: Movie,
    movieOnClick: (Int) -> Unit,
    movieOnSaveClick: (Movie) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = {movieOnClick(movie.id)},
        modifier = Modifier
            .padding(5.dp)
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
            .padding(5.dp)
    ) {
        MovieListItemContent(
            movie = movie,
            movieOnSaveClick = movieOnSaveClick
        )
    }
}

@Composable
fun MovieListItemContent(
    movie: Movie,
    movieOnSaveClick: (Movie) -> Unit
){
    AsyncImage(
        model = IMAGE_URL.plus(movie.posterPath),
        placeholder = painterResource(R.drawable.loading_image),
        contentDescription = null,
        modifier = Modifier
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .fillMaxWidth(),
        )
    Text(
        text = movie.title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 5.dp)
    )
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "★ ".plus(movie.voteAverage),
                fontSize = 16.sp,
            )
            Text(
                text = "Премьера: ".plus(movie.releaseDate),
                fontSize = 16.sp,
                lineHeight = 18.sp,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
        IconButton(
            onClick = {movieOnSaveClick(movie)},
            modifier = Modifier
                .size(36.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_bookmark_border_24),
                contentDescription = "Add Movie",
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieListItemPreview() {
    MovieComposeTheme {
        //MovieListScreen()
    }
}