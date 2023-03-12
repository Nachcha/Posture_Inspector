package com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository

import com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source.ReportDao
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.ReportRepository
import kotlinx.coroutines.flow.Flow

class ReportRepositoryImpl(
    private val dao: ReportDao
): ReportRepository {
    override fun getReportsByType(reportType: String): Flow<List<Report>> {
        return dao.getReportsByType(reportType)
    }

    override suspend fun getReportById(id: Int): Report? {
        return dao.getReportById(id)
    }

    override suspend fun insertReportData(report: Report) {
        return dao.insertReport(report)
    }

    override suspend fun deleteReport(report: Report) {
        return dao.deleteReport(report)
    }
}