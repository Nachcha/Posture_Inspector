package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report

sealed class AddDeleteReportsEvent {
    data class DeleteReport(val report: Report): AddDeleteReportsEvent()
    data class SaveBalanceReport(val report: Report): AddDeleteReportsEvent()
    data class SaveWalkingReport(val report: Report): AddDeleteReportsEvent()
}