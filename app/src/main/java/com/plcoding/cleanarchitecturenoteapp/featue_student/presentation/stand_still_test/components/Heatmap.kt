package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.components

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.utils.HeatMapDataObject
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.PointsL
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.PointsR

@Composable
fun HeatMap(
    modifier: Modifier = Modifier,
    dataObject: HeatMapDataObject,
) {
    val points = emptyList<Offset>()
    for (x in 0 until 600) {
        for (y in 0 until 500) {
            points.plus(Offset(x.toFloat(), y.toFloat()))
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .background(Color.Cyan)
            .padding(6.dp)
            .background(Color.Black),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .width(360.dp)
                .height(300.dp)
                .background(Color.Red)
                .padding(2.dp)
                .background(Color.Black),
        ) {
            val clipPath = Path().apply {
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                lineTo(0f, size.height)
                close()
            }
            val linePath = Path()

            for (i in 0 until dataObject.centerPoints.size) {
                if (i == 0) {
                    linePath.moveTo(
                        dataObject.centerPoints[i].x.toFloat(),
                        dataObject.centerPoints[i].y.toFloat()
                    )
                } else {
                    linePath.lineTo(
                        dataObject.centerPoints[i].x.toFloat(),
                        dataObject.centerPoints[i].y.toFloat()
                    )
                }
            }

            clipPath(clipPath) {
                PointsL.mapIndexed { index, it ->
                    if (dataObject.sensorL[index] > 0) {
                        drawCircle(
                            brush = gerGradient(
                                getRefPxValue(size.width, it.x),
                                getRefPxValue(size.width, it.y),
                                dataObject.sensorL[index] / 10,
                                (dataObject.sensorL[index] / 100).toDouble()
                            ),
                            center = Offset(
                                getRefPxValue(size.width, it.x),
                                getRefPxValue(size.width, it.y)
                            ),
                            radius = dataObject.sensorL[index] / 10
                        )
                    }
                }
                PointsR.mapIndexed { index, it ->
                    if (dataObject.sensorR[index] > 0) {
                        drawCircle(
                            brush = gerGradient(
                                getRefPxValue(size.width, it.x),
                                getRefPxValue(size.width, it.y),
                                dataObject.sensorR[index] / 10,
                                (dataObject.sensorR[index] / 100).toDouble()
                            ),
                            center = Offset(
                                getRefPxValue(size.width, it.x),
                                getRefPxValue(size.width, it.y)
                            ),
                            radius = dataObject.sensorR[index] / 10
                        )
                    }
                }

                drawPoints(
                    points = points,
                    pointMode = PointMode.Points,
                    color = Color.White,
                    colorFilter = ColorFilter.tint(Color.White),
                )
                
                drawPath(
                    path = linePath,
                    color = Color.Red,
                    alpha = 1f,
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }
    }
}

fun gerGradient(
    x: Float,
    y: Float,
    radius: Float,
    value: Double = 1.0
): Brush {
    return Brush.radialGradient(
        colors = listOf(
            Color(255, 0, 0, (255 * value).toInt()),
            Color(0, 255, 0, (255 * value).toInt()),
            Color(0, 0, 255, (255 * value).toInt()),
            Color(0x00FFFFFF)
        ),
        center = Offset(x, y),
        radius = radius
    )
}

fun getRefPxValue(
    width: Float,
    value: Int
): Float {
    return (width / 600f) * value
}
