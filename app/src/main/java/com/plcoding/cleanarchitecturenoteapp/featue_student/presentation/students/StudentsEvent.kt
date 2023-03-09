package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.students

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.StudentOrder

sealed class StudentsEvent {
    data class Order(val studentOrder: StudentOrder): StudentsEvent()
    data class DeleteStudent(val student: Student): StudentsEvent()
    object RestoreStudent: StudentsEvent()
    object ToggleOrderSelection: StudentsEvent()
}
