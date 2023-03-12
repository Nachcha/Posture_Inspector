package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student

sealed class StandStillTestEvent {
    data class FetchData(val student: Student) : StandStillTestEvent()
    data class InsertStandStillDataRow(val standStillData: StandStillData): StandStillTestEvent()
}