package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.walking_test

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.WalkingData

data class WalkingTestState (
    val walkingDataList: List<WalkingData> = emptyList(),
    val reportId: Int? = null,
    val dateTime: String? = "",
    val studentId: Int = -1,
    val reportType: String = "",
)