package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData

data class StandStillTestState(
    val standStillDataList: List<StandStillData> = emptyList(),
    val studentId: Int? = null,
)
