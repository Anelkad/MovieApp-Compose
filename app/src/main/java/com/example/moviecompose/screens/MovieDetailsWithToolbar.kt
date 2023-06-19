package com.example.moviecompose.screens

import IMAGE_URL
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.moviecompose.R
import com.example.moviecompose.models.Genre
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.ProductionCountry
import com.example.moviecompose.ui.theme.MovieComposeTheme
import java.security.AllPermission

@Composable
fun MovieDetailsWithToolbar(
    movie: MovieDetails = MovieDetails(
        1,
        "Kino",
        "Kino original",
        "asdf ".repeat(30),
        "2012-12-12",
        "https://lumiere-a.akamaihd.net/v1/images/p_thelittlemermaid_2023_final_796_94759fcc.jpeg",
        "https://lumiere-a.akamaihd.net/v1/images/p_thelittlemermaid_2023_final_796_94759fcc.jpeg",
        5.0F,
        120,
        listOf(ProductionCountry("JP", "Japan")),
        listOf(Genre(16, "мультфильм"), Genre(18, "анимация")),
        "Hello World",
        12000000,
        121
    )
) {
    val headerHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val toolbarHeightDp = 56.dp

    val scroll: ScrollState = rememberScrollState(800)

    val headerHeightPx = with(LocalDensity.current) { headerHeightDp.toPx()}
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeightDp.toPx()}

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(scroll, movie.posterPath)
        MovieDetailsContent(movie, scroll)
        Toolbar(scroll, headerHeightPx, toolbarHeightPx, movie)
    }
}

@Composable
fun Toolbar(scroll: ScrollState, headerHeightPx: Float, toolbarHeight: Float, movie: MovieDetails) {
    val toolbarBottom by remember {
        mutableStateOf(headerHeightPx - toolbarHeight)
    }

    val showToolbar by remember {
        derivedStateOf { scroll.value >= toolbarBottom }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        TopAppBar(
            title = {
                Text(
                    text = movie.title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = null
                    )
                }
            },
            backgroundColor = Color.White
        )
    }
}

@Composable
fun Header(scroll: ScrollState, moviePoster: String) {
    Column(
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth()
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 10f // Parallax effect
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ){
            AsyncImage(
                model = IMAGE_URL.plus(moviePoster),
                placeholder = painterResource(R.drawable.loading_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(40.dp)
                    .width(
                        (LocalConfiguration.current.screenWidthDp - scroll.value / 10).dp
                    )
            )
    }
}


@Composable
fun MovieDetailsContent(
    movie: MovieDetails,
    scroll: ScrollState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scroll)
    ) {
        val configuration = LocalConfiguration.current
        Spacer(modifier = Modifier.height(configuration.screenHeightDp.dp - 30.dp))

        Column(
            modifier = Modifier
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            MovieGeneralInfo(movie)
            ScheduleButton()
            CastInfo()

            RowOfIconButton()
        }

        Column(
            modifier = Modifier
                .background(Color.White)
        )  {
            MovieDetailsInfo(movie)
            KinopoiskRatingBlock(movie)
            HorizontalRowOfRating()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovieDetailsWithToolbarPreview() {
    MovieComposeTheme {
        MovieDetailsWithToolbar()
    }
}