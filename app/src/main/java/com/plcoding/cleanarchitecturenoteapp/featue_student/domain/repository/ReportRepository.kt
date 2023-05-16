package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.ReportType
import kotlinx.coroutines.flow.Flow

interface ReportRepository {

    fun getReportsByType(reportType: String, studentId: Int): Flow<List<Report>>

    suspend fun getReportById(id: Int): Report?

    suspend fun insertReportData(report: Report)

    suspend fun updateReportData(report: Report)

    suspend fun deleteReport(report: Report)
}