package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.walking_test.utils

import android.graphics.PointF

data class HeatMapDataObjectWalking(
    val sensorL: Array<Float>,
    val sensorR: Array<Float>,
    val centerPoints: List<PointF>,
)
