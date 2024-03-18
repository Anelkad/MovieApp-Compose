package com.example.moviecompose

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.util.Random
import kotlin.math.abs

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransformableImage(
    modifier: Modifier = Modifier,
    url: String,
    updateScale: (Float) -> Unit
) {
    BoxWithConstraints {
        var offset by remember { mutableStateOf(Offset.Zero) }
        var scale by remember { mutableFloatStateOf(1f) }
        val animOffset by animateOffsetAsState(
            targetValue = offset
        )
        val animScale by animateFloatAsState(
            targetValue = scale
        )
        val random = remember { Random() }
        val color =
            remember { Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)) }
        AsyncImage(
            modifier = modifier
                .graphicsLayer(
                    // zoom transformations
                    scaleX = animScale,
                    scaleY = animScale,
                    translationX = animOffset.x,
                    translationY = animOffset.y,
                )
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { /* NADA :) */ },
                    onDoubleClick = {
                        if (scale >= 2f) {
                            scale = 1f
                        } else scale = 3f
                    },
                )
                .pointerInput(Unit) {
//                    detectTapGestures (
//                        onDoubleTap = { tapOffset ->
//                            scale *= 2
//                            if (scale > 6) {
//                                scale = 1f
//                                offset = Offset.Zero
//                            } else {
//                                offset = Offset(
//                                    x = (constraints.maxWidth - tapOffset.x * 2),
//                                    y = (constraints.maxHeight - tapOffset.y * 2)
//                                )
//                            }
//                        }
//                    )
                    awaitEachGesture {
                        awaitFirstDown()
                        do {
                            val event = awaitPointerEvent()
                            scale *= event.calculateZoom()
                            if (scale > 1) {
                                if (scale > 5f) scale = 5f
                                val panChange = event.calculatePan()
                                val extraWidth = abs(scale - 1) * constraints.maxWidth
                                val extraHeight = abs(scale - 1) * constraints.maxHeight
                                val maxX = extraWidth / 2
                                val maxY = extraHeight / 2
                                offset = Offset(
                                    x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                                    y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
                                )
                            } else {
                                scale = 1f
                                offset = Offset.Zero
                            }
                            updateScale(scale)
                        } while (event.changes.any { it.pressed })
                    }
//                    detectTapGestures( // calculated formula
//                        onDoubleTap = { tapOffset ->
//                            scale *= 2
//                            if (scale > 6) {
//                                scale = 1f
//                                offset = Offset.Zero
//                            } else {
//                                offset = Offset(
//                                    x = (constraints.maxWidth - tapOffset.x * 2),
//                                    y = (constraints.maxHeight - tapOffset.y * 2)
//                                )
//                            }
//                        }
//                    )
                }
                .background(color)
                .fillMaxSize(),
            model = url,
            contentDescription = "studio image",
            contentScale = ContentScale.Fit
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun TransformableImagePreview() {
    val images = List<String>(5){"https://d27jswm5an3efw.cloudfront.net/app/uploads/2019/08/image-url-3.jpg"}
    val pagerState = rememberPagerState { images.size }
    var scale by remember {
        mutableFloatStateOf(1f)
    }
    Log.d("qwerty", "scale: $scale")
    val enableScroll by remember {
        derivedStateOf { scale == 1f }
    }
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = enableScroll
    ) { page ->
        TransformableImage(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    val screenWidth = size.width
                    detectTapGestures(
                        onPress = { offset ->
                            if (offset.x < screenWidth / 6) {
                                if (page > 0) {
                                    if (enableScroll) pagerState.scrollToPage(pagerState.currentPage - 1)
                                }
                            } else {
                                if (offset.x > screenWidth * 5 / 6) {
                                    if (page < pagerState.pageCount) {
                                        if (enableScroll) pagerState.scrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            }
                        }
                    )
                },
            updateScale = { scale = it },
            url = images[page]
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasPlay() {
    Canvas(
        modifier = Modifier
            .size(300.dp)
    ) {
        drawRect(
            color = Color.Black,
            size = size
        )
        drawRect(
            color = Color.Red,
            topLeft = Offset(125.dp.toPx(), 125.dp.toPx()),
            size = Size(50.dp.toPx(), 50.dp.toPx()),
            style = Stroke(
                width = 3.dp.toPx()
            )
        )
    }
}