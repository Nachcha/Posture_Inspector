package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util

import androidx.compose.ui.graphics.Color
import com.plcoding.cleanarchitecturenoteapp.ui.theme.FemaleColor
import com.plcoding.cleanarchitecturenoteapp.ui.theme.MaleColor


fun getBackgroundColor(gender: String): Color {
    return if (gender == "Female") {
        FemaleColor
    } else {
        MaleColor
    }
}

fun getInverseBackgroundColor(gender: String): Color {
    return if (gender == "Male") {
        FemaleColor
    } else {
        MaleColor
    }
}