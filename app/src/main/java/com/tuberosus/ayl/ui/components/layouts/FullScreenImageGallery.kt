package com.tuberosus.ayl.ui.components.layouts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.SubcomposeAsyncImage
import com.tuberosus.ayl.R
import com.tuberosus.ayl.ui.components.PlaceholderImage
import com.tuberosus.ayl.ui.theme.Blue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.abs

private const val MAX_ALPHA = 1f
private const val SCROLL_BACKGROUND_NORMALIZATION = 800f
private const val SCROLL_TOPBAR_NORMALIZATION = 400f
private const val MIN_ALPHA_REDUCTION = 0.0f
private const val MAX_ALPHA_REDUCTION_BACKGROUND = 0.4f
private const val MAX_ALPHA_REDUCTION_TOPBAR = 1f
private const val OFFSET_SCROLL_THRESHOLD = 20f
private const val ANIMATION_SPEC_DURATION = 300

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenImageGallery(
    images: List<String>,
    startIndex: Int,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (offsetY, backgroundAlpha, appBarAlpha) = rememberGalleryState()

    val isUserScrollEnable by remember(offsetY.value) {
        derivedStateOf { abs(offsetY.value) < OFFSET_SCROLL_THRESHOLD }
    }

    val pagerState = rememberPagerState(
        initialPage = startIndex,
        pageCount = { images.size }
    )

    FullScreenGalleryDialog(
        backgroundAlpha = backgroundAlpha,
        onDismiss = onDismiss,
        modifier = modifier
            .composed {
                verticalDismissGesture(
                    scope = rememberCoroutineScope(),
                    offsetY = offsetY,
                    onDismiss = onDismiss
                )
            },
        content = {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                pageSpacing = 8.dp,
                userScrollEnabled = isUserScrollEnable
            ) { page ->
                FullScreenImage(images[page], offsetY.value)
            }

            FullScreenTopBar(
                alpha = appBarAlpha,
                onDismiss = onDismiss,
            )
        }
    )
}

@Composable
private fun FullScreenGalleryDialog(
    backgroundAlpha: Float,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                        .copy(alpha = backgroundAlpha)
                )
        ) {
            content()
        }
    }
}

private fun Modifier.verticalDismissGesture(
    offsetY: Animatable<Float, AnimationVector1D>,
    onDismiss: () -> Unit,
    scope: CoroutineScope
): Modifier {
    return pointerInput(Unit) {
        detectVerticalDragGestures(
            onVerticalDrag = { _, dragAmount ->
                scope.launch { offsetY.snapTo(offsetY.value + dragAmount) }
            },
            onDragEnd = {
                if (abs(offsetY.value) > 300f) {
                    scope.launch {
                        offsetY.animateTo(
                            targetValue = offsetY.value * 2,
                            animationSpec = tween(ANIMATION_SPEC_DURATION)
                        )
                    }
                    onDismiss()
                } else {
                    scope.launch {
                        offsetY.animateTo(
                            0f,
                            animationSpec = tween(
                                durationMillis = ANIMATION_SPEC_DURATION,
                                easing = FastOutSlowInEasing
                            )
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun FullScreenImage(
    imageUrl: String,
    offsetY: Float,
) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxSize()
            .graphicsLayer { translationY = offsetY },
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Fit,
        loading = {
            PlaceholderImage()
        },
        error = {
            PlaceholderImage()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FullScreenTopBar(
    alpha: Float,
    onDismiss: () -> Unit,
    title: @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .alpha(alpha),
        title = title,
        actions = {
            IconButton(
                onClick = onDismiss
            ) {
                Icon(
                    modifier = Modifier
                        .size(36.dp),
                    painter = painterResource(R.drawable.ic_cancel),
                    contentDescription = stringResource(R.string.close),
                    tint = Blue
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
private fun rememberGalleryState(): Triple<Animatable<Float, AnimationVector1D>, Float, Float> {
    val offsetY = remember { Animatable(MIN_ALPHA_REDUCTION) }

    val backgroundAlpha by remember(offsetY.value) {
        derivedStateOf {
            MAX_ALPHA - (abs(offsetY.value) / SCROLL_BACKGROUND_NORMALIZATION)
                .coerceIn(MIN_ALPHA_REDUCTION, MAX_ALPHA_REDUCTION_BACKGROUND)
        }
    }

    val appBarAlpha by remember(offsetY.value) {
        derivedStateOf {
            MAX_ALPHA - (abs(offsetY.value) / SCROLL_TOPBAR_NORMALIZATION)
                .coerceIn(MIN_ALPHA_REDUCTION, MAX_ALPHA_REDUCTION_TOPBAR)
        }
    }

    return Triple(offsetY, backgroundAlpha, appBarAlpha)
}

@Preview(showBackground = true)
@Composable
private fun FullScreenImageGalleryPreview() {
    FullScreenImageGallery(
        images = emptyList(),
        startIndex = 0,
        onDismiss = {}
    )
}