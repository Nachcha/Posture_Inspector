package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.utils

import android.graphics.PointF

data class HeatMapDataObject(
    val sensorL: Array<Float>,
    val sensorR: Array<Float>,
    val centerPoints: List<PointF>,
)
