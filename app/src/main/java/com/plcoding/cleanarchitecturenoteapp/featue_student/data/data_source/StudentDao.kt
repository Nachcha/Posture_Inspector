package com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Query("SELECT * from student")
    fun getStudents() : Flow<List<Student>>

    @Query("SELECT * from student WHERE id= :id")
    suspend fun getStudentById(id: Int): Student?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStudent(student: Student)
}