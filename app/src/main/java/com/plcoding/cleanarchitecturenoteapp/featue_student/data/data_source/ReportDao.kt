package com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source

import androidx.room.*
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    @Query("SELECT * from report WHERE report_type= :reportType AND student_id= :studentId ORDER BY date_time DESC")
    fun getReportsByType(reportType: String,studentId: Int) : Flow<List<Report>>

    @Query("SELECT * from report WHERE id= :id")
    suspend fun getReportById(id: Int): Report?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report)

    @Delete
    suspend fun deleteReport(report: Report)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateReport(report: Report)
}