package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@Composable
fun HeatMap(
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints(
        modifier = modifier
            .background(Color.Cyan)
            .padding(6.dp)
            .background(Color.Black)
            .onSizeChanged {

            },
        contentAlignment = Alignment.Center,
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .width(360.dp)
                .height(300.dp)
                .background(Color.Red)
                .padding(2.dp)
                .background(Color.Black)
        ) {
            val clipPath = Path().apply {
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }

            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = size.width.coerceAtMost(size.height) / 10f
            clipPath(clipPath) {

                drawCircle(
                    brush = gerGradient(centerX, centerY, radius),
                    center = Offset(centerX, centerY),
                    radius = radius
                )

                drawCircle(
                    brush = gerGradient(centerX, centerY + 50, radius),
                    center = Offset(centerX, centerY + 50),
                    radius = radius
                )

                drawCircle(
                    brush = gerGradient(centerX, centerY - 50, radius),
                    center = Offset(centerX, centerY - 50),
                    radius = radius
                )
            }
        }
    }
}

fun gerGradient(x: Float, y: Float, radius: Float, value: Double = 1.0): Brush {
    return Brush.radialGradient(
        colors = listOf(Color(255, 255, 255, (255 * value).toInt()), Color(0x00FFFFFF)),
        center = Offset(x, y),
        radius = radius
    )
}