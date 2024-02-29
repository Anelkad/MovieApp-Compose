package com.example.moviecompose.movieList.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Colors
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecompose.R
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieListScreen(
    recyclerView: RecyclerView,
    modifier: Modifier = Modifier
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = true,
        onRefresh = {}
    )
    Box(modifier = modifier) {
        PullRefreshIndicator(
            refreshing = true,
            state = pullRefreshState,
            modifier = Modifier
                .statusBarsPadding()
                .align(Alignment.TopCenter)
        )
        AndroidView(
            factory = {
                recyclerView
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun SwipeRefreshPreview() {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = true,
        onRefresh = {}
    )
    Column {
        PullRefreshIndicator(
            refreshing = true,
            state = pullRefreshState,
            scale = true,
            modifier = Modifier
                .statusBarsPadding()
                .size(100.dp)
        )
        Image(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,

        )
        Spacer(modifier = Modifier.height(30.dp))
    }
}
