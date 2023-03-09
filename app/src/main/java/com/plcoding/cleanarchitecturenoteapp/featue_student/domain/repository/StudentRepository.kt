package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import kotlinx.coroutines.flow.Flow

interface StudentRepository {

    fun getStudents(): Flow<List<Student>>

    suspend fun getStudentById(id: Int): Student?

    suspend fun insertStudent(student: Student)

    suspend fun deleteStudent(student: Student)
}