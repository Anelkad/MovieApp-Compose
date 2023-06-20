package com.example.moviecompose.screens

import IMAGE_URL
import android.graphics.ColorMatrixColorFilter
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviecompose.R
import com.example.moviecompose.models.Genre
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.ProductionCountry
import com.example.moviecompose.ui.theme.MovieComposeTheme


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
    ),
    onBackClick: () -> Unit = {}
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
        Toolbar(scroll, headerHeightPx, toolbarHeightPx, movie,onBackClick)
    }
}

@Composable
fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeight: Float,
    movie: MovieDetails,
    onBackClick: () -> Unit
) {
    val toolbarBottom by remember {
        mutableStateOf(headerHeightPx - toolbarHeight)
    }

    val showToolbar by remember {
        derivedStateOf { scroll.value >= toolbarBottom }
    }
    TopAppBar(
            title = {
                Text(
                    text = if (showToolbar) movie.title else "",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_24),
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_connected_tv_24),
                        contentDescription = null
                    )
                }
                if (showToolbar)
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Share, contentDescription = null)
                }
            },
            modifier = Modifier.background(
                color =  if (showToolbar) Color.White else Color.Transparent
            ),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )

}

@Composable
fun Header(scroll: ScrollState, moviePoster: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = IMAGE_URL.plus(moviePoster),
            placeholder = painterResource(R.drawable.loading_image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix().apply {
                    setToScale(1f, 1f, 1f, 0.5f)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .blur(30.dp)
        )
        Column(
            modifier = Modifier
                .padding(40.dp)
                .fillMaxWidth()
                .graphicsLayer {
                    translationY = -scroll.value.toFloat() / 10f // Parallax effect
                }
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            AsyncImage(
                model = IMAGE_URL.plus(moviePoster),
                placeholder = painterResource(R.drawable.loading_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(
                        (LocalConfiguration.current.screenWidthDp - scroll.value / 10).dp
                    )
            )
        }
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
                .background(Color.White)
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 20.dp)
                    .background(Color.LightGray)
                    .width(50.dp)
                    .height(5.dp)
            )

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