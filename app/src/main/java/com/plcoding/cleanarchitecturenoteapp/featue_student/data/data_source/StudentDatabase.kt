package com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.WalkingData

@Database(
    entities = [
        Student::class,
        Report::class,
        StandStillData::class,
        WalkingData::class
    ],
    version = 1
)
abstract class StudentDatabase : RoomDatabase() {
    abstract val studentDao: StudentDao
    abstract val standStillDataDao: StandStillDataDao
    abstract val walkingDataDao: WalkingDataDao
    abstract val reportDao: ReportDao

    companion object {
        const val DATABASE_NAME = "students_db"
    }
}