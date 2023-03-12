package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "report",
    foreignKeys = [ForeignKey(
        entity = Student::class,
        childColumns = ["student_id"],
        parentColumns = ["id"]
    )]
)
data class Report(
    val report_type: String,
    val student_id: Int,
    val date_time: String?,
    @PrimaryKey val id: Int? = null,
) {
    companion object {
        val reportTypes = listOf("Walking", "Balance")
    }
}

class InvalidReportException(message: String): Exception(message)
