package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.ReportRepository

class DeleteReport(
    private val repository: ReportRepository
) {
    suspend operator fun invoke(report: Report){
        repository.deleteReport(report)
    }
}