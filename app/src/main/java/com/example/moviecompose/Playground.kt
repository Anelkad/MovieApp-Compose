package com.example.moviecompose

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import java.util.Random
import kotlin.math.abs

@Composable
fun TransformableImage(
    modifier: Modifier = Modifier,
    scale: Float = 1f,
    updateScale: (Float) -> Unit
) {
    BoxWithConstraints {
        var offset by remember { mutableStateOf(Offset.Zero) }
        var scale by remember { mutableFloatStateOf(scale) }
        val transformableState =
            rememberTransformableState { zoomChange, panChange, _ ->
                scale *= zoomChange
                updateScale(scale)

                val extraWidth = abs(scale - 1) * constraints.maxWidth
                val extraHeight = abs(scale - 1) * constraints.maxHeight

                val maxX = extraWidth / 2
                val maxY = extraHeight / 2

                offset = if (scale == 1f) {
                    Offset(0f, 0f)
                } else {
                    Offset(
                        x = (offset.x + scale * panChange.x).coerceIn(-maxX, maxX),
                        y = (offset.y + scale * panChange.y).coerceIn(-maxY, maxY),
                    )
                }
            }
        val animOffset by animateOffsetAsState(
            targetValue = offset
        )
        val animScale by animateFloatAsState(
            targetValue = if (transformableState.isTransformInProgress) {
                if (scale >= 1F) scale else {
                    scale = 1f
                    updateScale(1f)
                    1F
                }
            } else {
                when {
                    scale < 1F -> {
                        offset = Offset.Zero
                        scale = 1F
                        updateScale(1f)
                        1f
                    }
                    scale > 5F -> {
                        updateScale(5f)
                        scale = 5F
                        5F
                    }
                    else -> scale
                }
            }
        )
        val random = remember { Random() }
        val color = remember { Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)) }
        Image(
            modifier = modifier
                .graphicsLayer(
                    // zoom transformations
                    scaleX = animScale,
                    scaleY = animScale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .transformable(
                    transformableState,
                    enabled = scale != 1f
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { tapOffset ->
                            scale *= 3
                            if (scale > 5) {
                                scale = 1f
                                offset = Offset.Zero
                            }
                        }
                    )
                }
                .background(color)
                .fillMaxSize(),
            imageVector = Icons.Default.DateRange,
            contentDescription = "studio image",
            contentScale = ContentScale.Fit
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun TransformableImagePreview() {
    val pagerState = rememberPagerState { 5 }
    var scale by remember {
        mutableFloatStateOf(1f)
    }
    Log.d("qwerty enable", scale.toString())
    val enableScroll by remember {
        derivedStateOf { scale == 1f }
    }
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = enableScroll
    ) {
        page ->
        Log.d("qwerty", enableScroll.toString())
        TransformableImage(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(enableScroll) {
                    val screenWidth = size.width
                    detectTapGestures(
                        onPress = { offset ->
                            if (offset.x < screenWidth / 6) {
                                if (page > 0) {
                                    pagerState.scrollToPage(pagerState.currentPage - 1)
                                }
                            } else {
                                if (offset.x > screenWidth * 5 / 6) {
                                    if (page < pagerState.pageCount) {
                                        pagerState.scrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            }
                        }
                    )
                },
            updateScale = { scale = it }
        )
    }
}