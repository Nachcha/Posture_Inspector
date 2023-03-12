package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util

sealed class ReportType {
    object StandStill : ReportType()
    object Walking : ReportType()
}