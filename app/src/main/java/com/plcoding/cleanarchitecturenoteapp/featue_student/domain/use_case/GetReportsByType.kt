package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.ReportRepository
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.StudentOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetReportsByType(
    private val repository: ReportRepository
) {
    operator fun invoke(
        reportType: String = "Balance"
    ): Flow<List<Report>> {
        return repository.getReportsByType(reportType)
    }
}