package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.students

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.StudentOrder

data class StudentsState(
    val students: List<Student> = emptyList(),
    val studentOrder: StudentOrder = StudentOrder.Name(OrderType.Ascending),
    val isStudentsSectionVisible: Boolean = false
)
