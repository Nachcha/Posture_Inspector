package com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student

@Database(
    entities = [Student::class],
    version = 1
)
abstract class StudentDatabase: RoomDatabase() {
    abstract val studentDao: StudentDao

    companion object{
        const val DATABASE_NAME = "students_db"
    }
}