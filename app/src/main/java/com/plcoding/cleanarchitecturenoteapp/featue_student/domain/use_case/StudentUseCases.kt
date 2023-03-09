package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

data class StudentUseCases(
    val getStudents: GetStudents,
    val deleteStudent: DeleteStudent,
    val addStudent: AddStudent,
    val getStudent: GetStudent
)
