package com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository

import com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source.StudentDao
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StudentRepository
import kotlinx.coroutines.flow.Flow

class StudentRepositoryImpl(
    private val dao: StudentDao
) : StudentRepository {
    override fun getStudents(): Flow<List<Student>> {
        return dao.getStudents()
    }

    override suspend fun getStudentById(id: Int): Student? {
        return dao.getStudentById(id)
    }

    override suspend fun insertStudent(student: Student) {
        dao.insertStudent(student)
    }

    override suspend fun deleteStudent(student: Student) {
        dao.deleteStudent(student)
    }
}