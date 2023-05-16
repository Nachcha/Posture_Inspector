@file:OptIn(ExperimentalAnimationApi::class, ExperimentalAnimationApi::class)

package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.shared_components

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.getBackgroundColor
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.getInverseBackgroundColor
import com.plcoding.cleanarchitecturenoteapp.ui.theme.FemaleColor
import com.plcoding.cleanarchitecturenoteapp.ui.theme.MaleColor

@Composable
fun AnimatedCounter(
    count: Int,
    maxCount: Int,
    modifier: Modifier = Modifier
        .height(100.dp)
        .width(100.dp),
    textStyle: TextStyle = MaterialTheme.typography.body1,
) {

    var oldCount by remember {
        mutableStateOf(count)
    }

    SideEffect {
        oldCount = count
    }

    Box(
        modifier = modifier,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            val radius = size.width/2
            drawCircle(
                center = Offset(radius, radius),
                radius = radius,
                brush = Brush.radialGradient(
                    colorStops = arrayOf(
                        Pair(0.5f, getBackgroundColor("Male")),
                        Pair(1f, getInverseBackgroundColor("Male")),
                    ),
                    radius = radius,
                    tileMode = TileMode.Repeated,
                    center = Offset(radius,radius)
                )
            )
            drawArc(
                startAngle =-90f,
                sweepAngle = (360*(count.toFloat()/maxCount)).toFloat(),
                useCenter = false,
                color = Color.Green,
                size = Size(radius*2f,radius*2f),
                topLeft = Offset(0f,0f),
                style = Stroke(50f, cap = StrokeCap.Round)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val countString = count.toString()
            val oldCountString = oldCount.toString()
            for (i in countString.indices) {
                val oldChar = oldCountString.getOrNull(i)
                val newChar = countString[i]
                val char = if (oldChar == newChar) {
                    oldCountString[i]
                } else {
                    countString[i]
                }
                AnimatedContent(
                    targetState = char,
                    transitionSpec = {
                        slideInVertically(initialOffsetY = { -it }) with slideOutVertically(
                            targetOffsetY = { it })
                    }
                ) { char ->
                    Text(
                        text = char.toString(),
                        style = textStyle,
                        softWrap = false,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}