package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StudentRepository

class GetStudent(
    private val repository: StudentRepository
) {
    suspend operator fun invoke(id: Int): Student? {
        return repository.getStudentById(id)
    }
}