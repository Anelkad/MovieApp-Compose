package com.example.moviecompose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TransformableImage(
    modifier: Modifier = Modifier
) {
    BoxWithConstraints {
        var offset by remember { mutableStateOf(Offset.Zero) }
        var scale by remember { mutableStateOf(1f) }
        val transformableState =
            rememberTransformableState { zoomChange, panChange, _ ->
                scale *= zoomChange

                val extraWidth = (scale - 1) * constraints.maxWidth
                val extraHeight = (scale - 1) * constraints.maxHeight

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
            targetValue = if (!transformableState.isTransformInProgress && scale > 5F) {
                offset = Offset.Zero
                scale = 5F
                5F
            } else {
                scale
            }
        )
        Image(
            modifier = modifier
                .graphicsLayer(
                    // zoom transformations
                    scaleX = animScale,
                    scaleY = animScale,
                    translationX = offset.x,
                    translationY = offset.y,
                )
                .transformable(transformableState)
                .background(Color.Blue)
                .fillMaxSize(),
            imageVector = Icons.Default.DateRange,
            contentDescription = "studio image",
            contentScale = ContentScale.Fit
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransformableImagePreview() {
    TransformableImage()
}