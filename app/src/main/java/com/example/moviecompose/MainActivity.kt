package com.example.moviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.moviecompose.ui.theme.MovieComposeTheme
import com.example.okhttp.api.RetrofitService
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = RetrofitService()

        lifecycleScope.launch {
            val movieList = service.getMovieList(13).results
            setContent {
                MovieComposeTheme {
                    MovieListScreen(movieList)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieComposeTheme {
        MovieListScreen()
    }
}