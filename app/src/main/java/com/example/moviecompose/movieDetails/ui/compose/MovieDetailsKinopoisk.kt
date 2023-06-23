package com.example.moviecompose.movieDetails.ui.compose

import IMAGE_URL
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.moviecompose.R
import com.example.moviecompose.models.*
import com.example.moviecompose.ui.theme.MovieComposeTheme

@Composable
fun MovieGeneralInfo(movie: MovieDetails){
    Text(
        text = movie.title,
        textAlign = TextAlign.Center,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        textAlign = TextAlign.Center,
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(R.color.rating)
                )
            ) {
                append(movie.voteAverage.toString())
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            ) {
                append(movie.voteCount.toString())
            }
            append(" ")
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    color = Color.Black
                )
            ) {
                append(movie.originalTitle)
            }
        }
    )

    Text(
        textAlign = TextAlign.Center,
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            ) {
                append(movie.releaseDate.take(4))
                append(" ")
                append(movie.genres.map {it.name}.joinToString(", "))
            }
        }
    )

    Text(
        textAlign = TextAlign.Center,
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            ) {
                append(movie.productionCountries[0].name)
                append(" ")
                append("${movie.runtime / 60} ч ${movie.runtime % 60} мин")
            }
        }
    )
}

@Composable
fun ScheduleButton(){
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(vertical = 15.dp, horizontal = 20.dp)
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
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "Расписание и билеты",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CastInfo(){
    Box(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .fillMaxWidth()
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontSize = 18.sp
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
}

@Composable
fun IconButton(
    image: Painter,
    label: String,
    iconPadding: Dp
    ){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = image,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .size(35.dp)
                .padding(iconPadding)
        )
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(horizontal = 5.dp)
        )
    }
}

@Composable
fun RowOfIconButton(){
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp)
    ) {
        IconButton(
            image = painterResource(R.drawable.baseline_star_24),
            label = "Оценить",
            iconPadding = 3.dp
        )
        IconButton(
            image = painterResource(R.drawable.baseline_bookmark_24),
            label = "Буду смотреть",
            iconPadding = 5.dp
        )
        IconButton(
            image = painterResource(R.drawable.baseline_share_24),
            label = "Поделиться",
            iconPadding = 5.dp
        )
        IconButton(
            image = painterResource(R.drawable.baseline_more_horiz_24),
            label = "Еще",
            iconPadding = 0.dp
        )
    }
}

@Composable
fun MovieDetailsInfo(
    movie: MovieDetails
){
    Column(
        modifier = Modifier
            .padding(
                start = 20.dp,
                end = 20.dp,
                bottom = 20.dp
            )
    ) {
        Text(
            text = movie.overview,
            textAlign = TextAlign.Left,
            fontSize = 18.sp,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row{
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
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

@Composable
fun KinopoiskRatingBlock(
    movie: MovieDetails
){
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "Рейтинг Кинопоиска",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 23.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.light_gray)),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = movie.voteAverage.toString(),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 70.sp,
                color = colorResource(R.color.rating),
                modifier = Modifier.height(80.dp)
            )
            Text(
                text = "${movie.voteCount} оценки",
                fontSize = 20.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))
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
                                colorResource(R.color.light_yellow)
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite
                        ),
                        shape = RoundedCornerShape(50)
                    )

            ) {
                Text(
                    text = "Оценить",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun HorizontalRowOfRating(){
    Row(
        modifier = Modifier
            .padding(vertical = 20.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp)
                .background(colorResource(R.color.light_gray))
                .padding(vertical = 20.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "7.8",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
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
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .background(colorResource(R.color.light_gray))
                .padding(vertical = 20.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "83%",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Положительных\nрецензий",
                lineHeight = 20.sp
            )
        }
    }
}


@Composable
fun VideoContent(video: Video){
    val url = "https://www.youtube.com/watch?v=${video.key}"
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                500
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            webChromeClient = WebChromeClient()
        }
    }, update = {
        it.loadUrl(url)
    })
}

@Composable
fun VideoItem(video: Video){
    Column(
        modifier = Modifier
            .padding(start = 20.dp)
            .width(250.dp)
    ){
        VideoContent(video)
        Text(
            text = video.name,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = video.published_at.take(10),
            color = Color.Gray
        )
    }
}

@Composable
fun VideoBlock(videoResponse: MovieVideoResponse){
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Трейлеры и тизеры",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 23.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
            Row{
                Text(
                    text = videoResponse.results.size.toString(),
                    color = colorResource(R.color.orange),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Icon(
                    painter = painterResource(R.drawable.baseline_keyboard_arrow_right_24),
                    contentDescription = null,
                    tint = colorResource(R.color.orange),
                    modifier = Modifier
                        .size(30.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .horizontalScroll(rememberScrollState())
        ){
            videoResponse.results.forEach{
                video ->
                VideoItem(video)
            }
            Spacer(modifier = Modifier.width(20.dp))
        }
        Spacer(modifier = Modifier.height(20.dp))
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
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = IMAGE_URL.plus(movie.posterPath),
                placeholder = painterResource(R.drawable.loading_image),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(5.dp))

            MovieGeneralInfo(movie)
            ScheduleButton()
            CastInfo()

            RowOfIconButton()

        }

        Column {
            MovieDetailsInfo(movie)
            KinopoiskRatingBlock(movie)
            HorizontalRowOfRating()
        }
    }

}

@Preview(showBackground = true, heightDp = 2000)
@Composable
fun MovieDetailsKinopoiskPreview() {
    MovieComposeTheme {
        //MovieDetailsKinopoisk()
    }
}