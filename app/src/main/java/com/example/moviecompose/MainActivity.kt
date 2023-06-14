package com.example.moviecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviecompose.models.BottomNavItem
import com.example.moviecompose.models.Movie
import com.example.moviecompose.ui.theme.MovieComposeTheme
import com.example.okhttp.api.RetrofitService

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = RetrofitService()
        //todo как вытащить movieList
//        lifecycleScope.launch {
//            val movieList = service.getMovieList(13).results
//            setContent {
//                MovieComposeTheme {
//                    MovieListScreen(movieList)
//                }
//            }
//        }


        setContent {
                MovieComposeTheme {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Home",
                                        route = "home",
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = "Saved",
                                        route = "saved",
                                        icon = Icons.Default.Favorite
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                })
                        }
                    ){ contentPadding ->
                        Surface(modifier = Modifier.padding(contentPadding)) {
                            Navigation(navController = navController)
                        }
                    }
                }
            }
    }
}

@Composable
fun Navigation(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        val service = RetrofitService()
        composable("home"){
            var movieList = ArrayList<Movie>()
            LaunchedEffect(Unit) {
                movieList = service.getMovieList(13).results
            }
            MovieListScreen(movieList)
        }
        composable("saved"){
            SavedMovieListScreen()
        }
    }
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
){
    val backStateEntry = navController.currentBackStackEntryAsState()

    BottomNavigation(
        modifier = Modifier,
        backgroundColor = Color.White,
        elevation = 5.dp
    ){
        items.forEach{ item ->
            val selected = item.route == backStateEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item)},
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray ,
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )
                        if (selected){
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        }
                    }
                }
            )
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