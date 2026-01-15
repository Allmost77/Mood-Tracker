package com.example.moodtracker.Home

import android.content.Context
import android.view.RoundedCorner
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moodtracker.R
import com.example.moodtracker.ui.theme.MoodTrackerTheme
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel() ) {
    var sliderPosition by remember { mutableFloatStateOf(0f) }
    val text_of_state by viewModel.mood.collectAsState()
    val fraction = sliderPosition / 4
    val animatedFraction by animateFloatAsState(
        targetValue = sliderPosition / 4f,
        animationSpec = tween(400)
    )
    var hasChanged by remember { mutableStateOf(false) }
    var context = LocalContext.current



    Column(Modifier.fillMaxSize()) {
        Text(
        text = stringResource(R.string.hello_user),
        fontSize = 50.sp
        )
        Spacer(Modifier.height(80.dp))


        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp)) {
                Text(
                    text = stringResource(R.string.question_about_state),
                    fontSize = 30.sp
                )
                Spacer(modifier = Modifier.height(50.dp))
                Text(
                   text = stringResource(id = viewModel.moodTextRes()),
                   fontSize = 20.sp

                )
                Box(
                    Modifier.fillMaxWidth()
                ){
                    GradientTrack(
                        fraction = animatedFraction,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    Slider(
                        value = sliderPosition,
                        onValueChange = { newValue ->
                            sliderPosition = newValue
                            (viewModel::onMoodChanged) (newValue.toInt())
                            hasChanged = true    },
                        valueRange = 0f..4f,
                        steps = 3,
                        colors = SliderDefaults.colors(
                            activeTickColor = Color.Transparent,
                            inactiveTrackColor = Color.Transparent,
                            thumbColor = Color.Gray,
                            activeTrackColor = Color.Transparent,



                        )
                    )
                }


                Spacer(modifier = Modifier.height(12.dp))

                AnimatedVisibility(
                    visible = hasChanged,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Button(
                        onClick =  {
                            viewModel.onMoodChanged(sliderPosition.toInt())
                            hasChanged = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(context.getText(R.string.Button_apply_state).toString())
                    }
                }

            }

        }
    }
}

@Composable
fun GradientTrack(
    fraction: Float,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(RoundedCornerShape(50))
            .background(Color.LightGray.copy(alpha = 0.2f))
    ) {
        val width = size.width
        val f = fraction.coerceIn(0f, 1f)


        val startX = width * (0.5f - f)
        val endX = width * (1.5f - f)

        val brush = Brush.horizontalGradient(
            colors = listOf(
                Color(0xFF2979FF),
                Color( 0xFFFF5252)
            ),
            startX = startX,
            endX = endX
        )

        drawRect(brush = brush)
    }
}

