package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidStudentException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StudentRepository

class AddStudent(
    private val repository: StudentRepository
) {
    @Throws(InvalidStudentException::class)
    suspend operator fun invoke(student: Student) {
        if (student.name.isBlank()){
            throw InvalidStudentException("Name of the student can't be empty.")
        }
        if (student.age <= 16 || student.age >=24){
            throw InvalidStudentException("Age of the student should be between 16-24 years.")
        }
        if (student.weight < 0){
            throw InvalidStudentException("Weight of the student can't be zero.")
        }
        if (student.height < 0){
            throw InvalidStudentException("Height of the student can't be zero.")
        }
        repository.insertStudent(student)
    }
}