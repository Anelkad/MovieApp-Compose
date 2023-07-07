package com.example.moviecompose.movieDetails.ui.compose

import IMAGE_URL
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import com.example.moviecompose.movieDetails.ui.modelUI.GenreUI
import com.example.moviecompose.movieDetails.ui.modelUI.MovieDetailsUI
import com.example.moviecompose.movieDetails.ui.modelUI.MovieVideoResponseUI
import com.example.moviecompose.movieDetails.ui.modelUI.ProductionCountryUI
import com.example.moviecompose.movieDetails.ui.modelUI.VideoUI
import com.example.moviecompose.ui.theme.MovieComposeTheme


@Composable
fun MovieDetailsWithToolbar(
    movie: MovieDetailsUI = MovieDetailsUI(
        1,
        "Kino",
        "Kino original",
        "asdf ".repeat(30),
        "2012-12-12",
        "https://lumiere-a.akamaihd.net/v1/images/p_thelittlemermaid_2023_final_796_94759fcc.jpeg",
        "https://lumiere-a.akamaihd.net/v1/images/p_thelittlemermaid_2023_final_796_94759fcc.jpeg",
        5.0F,
        120,
        listOf(ProductionCountryUI("JP", "Japan")),
        listOf(GenreUI(16, "мультфильм"), GenreUI(18, "анимация")),
        "Hello World",
        12000000,
        121
    ),
    videos: MovieVideoResponseUI = MovieVideoResponseUI(
        1,
        List(5){
            VideoUI(
                "1",
                "d",
                "1",
                "jUV5iWAqyCA",
                "Video",
                true,
                "ewf",
                "feq",
                1000,
                "dqw"
            )
        }
    ),
    onBackClick: () -> Unit = {}
) {
    val headerHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val toolbarHeightDp = 56.dp

    val scroll: ScrollState = rememberScrollState(800)

    val headerHeightPx = with(LocalDensity.current) { headerHeightDp.toPx() }
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeightDp.toPx() }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(scroll, movie.posterPath)
        MovieDetailsContent(movie, scroll, videos)
        Toolbar(scroll, headerHeightPx, toolbarHeightPx, movie, onBackClick)
    }
}

@Composable
fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeight: Float,
    movie: MovieDetailsUI,
    onBackClick: () -> Unit
) {
    val toolbarBottom by remember {
        mutableStateOf(headerHeightPx - toolbarHeight)
    }

    val alpha: Float by animateFloatAsState(
        targetValue = if (scroll.value >= toolbarBottom) 1f else 0f,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (scroll.value >= toolbarBottom) Color.White else Color.Transparent,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    TopAppBar(
        title = {
            Text(
                modifier = Modifier.alpha(alpha = alpha),
                text = movie.title,
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
            IconButton(
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_connected_tv_24),
                    contentDescription = null
                )
            }
            if (alpha == 1f) {
                IconButton(
                    modifier = Modifier.alpha(alpha = alpha),
                    onClick = {}
                ) {
                    Icon(Icons.Default.Share, contentDescription = null)
                }
            }
        },
        backgroundColor = backgroundColor,
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
    movie: MovieDetailsUI,
    scroll: ScrollState,
    videos: MovieVideoResponseUI
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
        ) {
            MovieDetailsInfo(movie)
            VideoBlock(videos)
            KinopoiskRatingBlock(movie)
            HorizontalRowOfRating()
        }
    }

}

@Preview(showBackground = true, heightDp = 2000)
@Composable
fun MovieDetailsWithToolbarPreview() {
    MovieComposeTheme {
        MovieDetailsWithToolbar()
    }
}