package com.learning.composecourseyt

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    //condition animation animate or not
    var animationPlayed by remember {
        mutableStateOf(false)
    }
    //animate float value
    val curPercentage = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = percentage) {
        curPercentage.animateTo(
            percentage,
            animationSpec = tween(
                durationMillis = animDuration,
                delayMillis = animDelay
            )
        )
    }


//    LaunchedEffect(key1 = true) { //set a key to true, that mean it never change and this launch run only one time
//        animationPlayed = true //trigger event
//    }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(radius * 2f)) {
        Canvas( // canvas use to draw
            modifier = Modifier.size(radius * 2f)
        ) {
            drawArc(
                color = color,
                startAngle = -90f,
                360 * curPercentage.value,
                useCenter = false, //remove the center line
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round), //style a stroke
            )

        }
        Text(
            text = (curPercentage.value * 360).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }

}