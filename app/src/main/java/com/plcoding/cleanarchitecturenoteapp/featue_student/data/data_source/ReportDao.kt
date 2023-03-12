package com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source

import androidx.room.*
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Query("SELECT * from report WHERE report_type= :reportType")
    fun getReportsByType(reportType: String) : Flow<List<Report>>

    @Query("SELECT * from report WHERE id= :id")
    suspend fun getReportById(id: Int): Report?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)
}