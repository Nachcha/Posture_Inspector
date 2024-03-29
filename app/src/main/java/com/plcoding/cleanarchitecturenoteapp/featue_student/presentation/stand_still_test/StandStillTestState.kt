package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData

data class StandStillTestState(
    val standStillDataList: List<StandStillData> = emptyList(),
    val reportId: Int? = null,
    val dateTime: String? = "",
    val studentId: Int = -1,
    val reportType: String = "",
)
