package com.example.moviecompose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.moviecompose.R
import com.example.moviecompose.models.Genre
import com.example.moviecompose.models.MovieDetails
import com.example.moviecompose.models.ProductionCountry
import com.example.moviecompose.ui.theme.MovieComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsKinopoisk(
    movie: MovieDetails = MovieDetails(
        1,
        "Kino",
        "Kino original",
        "asdf ".repeat(30),
        "12-12-2012",
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
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        MovieDetailsKinopoiskContent(
            movie = movie,
            modifier = Modifier.padding(contentPadding)
        )

    }
}

@Composable
fun MovieDetailsKinopoiskContent(
    movie: MovieDetails,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = movie.posterPath,
            placeholder = painterResource(R.drawable.loading_image),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = movie.title,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Row {
            Text(
                text = movie.voteAverage.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.rating)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = movie.voteCount.toString(),
                fontSize = 18.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = movie.originalTitle,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
        Row {
            Text(
                text = "${movie.releaseDate.takeLast(4)},",
                fontSize = 18.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = movie.genres.map { it.name }.joinToString(", "),
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
        Row {
            Text(
                text = "${movie.productionCountries[0].name},",
                fontSize = 18.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "${movie.runtime / 60} ч ${movie.runtime % 60} мин",
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            colorResource(R.color.orange),
                            colorResource(R.color.orange),
                            colorResource(R.color.orange),
                            colorResource(R.color.orange),
                            colorResource(R.color.light_yellow)
                        ),
                        start = Offset(0f, Float.POSITIVE_INFINITY),
                        end = Offset(Float.POSITIVE_INFINITY, 0f)
                    ),
                    shape = RoundedCornerShape(50)
                )

        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_local_movies_24),
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(30.dp)
                    .padding(horizontal = 3.dp)
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "Расписание и билеты",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = Modifier.width(250.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Gray
                        )
                    ) {
                        append("В ролях ")
                        append(List(4) { "qwerty" }.joinToString(", "))
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(" и другие ")
                        }
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier

                .padding(vertical = 5.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_star_24),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Оценить",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_bookmark_24),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Буду смотреть",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_share_24),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(30.dp)

                )
                Text(
                    text = "Поделиться",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)
                    .clickable { }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_more_horiz_24),
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(30.dp)

                )
                Text(
                    text = "Еще",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            modifier = Modifier
                .padding(horizontal = 10.dp),
            text = movie.overview,
            textAlign = TextAlign.Left,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = "Все детали о фильме",
                color = colorResource(R.color.orange),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Icon(
                painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null,
                tint = colorResource(R.color.orange),
                modifier = Modifier.size(30.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Рейтинг Кинопоиска",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            modifier = Modifier.padding(5.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(colorResource(R.color.light_gray)),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = movie.voteAverage.toString(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 70.sp,
                modifier = Modifier,
                color = colorResource(R.color.rating)
            )
            Text(
                text = "${movie.voteCount} оценки",
                fontSize = 20.sp,
                modifier = Modifier
                    ,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .padding(horizontal = 70.dp)
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                colorResource(R.color.orange),
                                colorResource(R.color.orange),
                                colorResource(R.color.orange),
                                colorResource(R.color.orange),
                                colorResource(R.color.orange),
                                colorResource(R.color.orange),
                                colorResource(R.color.orange),
                                colorResource(R.color.yellow),
                                colorResource(R.color.light_yellow)
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        ),
                        shape = RoundedCornerShape(50)
                    )

            ) {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = "Оценить",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Row(
            modifier = Modifier
                .padding(10.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .background(colorResource(R.color.light_gray))
                    .padding(10.dp)
            ) {
                Text(
                    text = "7.8",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(5.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Рейтинг IMDb"
                    )
                    Text(
                        text = "747 оценок",
                        color = Color.Gray
                    )
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Row(
                modifier = Modifier
                    .background(colorResource(R.color.light_gray))
                    .padding(10.dp)
            ) {
                Text(
                    text = "83%",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(5.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Положительных\nрецензий",
                    lineHeight = 20.sp
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailsKinopoiskPreview() {
    MovieComposeTheme {
        MovieDetailsKinopoisk()
    }
}