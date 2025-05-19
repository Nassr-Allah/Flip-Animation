package com.nassrallah.flipanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nassrallah.flipanimation.ui.theme.FlipAnimationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlipAnimationTheme {
                var isFront by remember {
                    mutableStateOf(true)
                }
                val rotation by animateFloatAsState(if (isFront) 0f else 180f, animationSpec = tween(2000))

                Box(
                    modifier = Modifier.fillMaxSize().background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.3f)
                            .graphicsLayer {
                                rotationX = rotation
                                cameraDistance = 14 * density
                            }
                            .clickable {
                                isFront = !isFront
                            },
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        if (rotation < 90f) {
                            Image(
                                painter = painterResource(R.drawable.front),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Image(
                                painter = painterResource(R.drawable.back),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        rotationX = 180f
                                    }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun FlippableCard(modifier: Modifier = Modifier) {

    var rotation by remember {
        mutableStateOf(1f)
    }

    val state = rememberTransformableState { zoomChange, panChange, rotationChange ->
        rotation += rotationChange
    }

    BoxWithConstraints(
        modifier = modifier
            .background(
                color = Color.Blue,
                shape = RoundedCornerShape(20.dp)
            )
            .graphicsLayer {
                rotationX = rotation
            }
            .transformable(state)
    ) {}
}