package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidStudentException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.ReportRepository

class UpdateReport (
    private val repository: ReportRepository
) {
    @Throws(InvalidStudentException::class)
    suspend operator fun invoke(report: Report) {
        repository.updateReportData(report)
    }
}