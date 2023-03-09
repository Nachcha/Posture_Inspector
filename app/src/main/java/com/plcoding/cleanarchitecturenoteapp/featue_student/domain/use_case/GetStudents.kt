package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StudentRepository
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.StudentOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetStudents(
    private  val repository: StudentRepository
) {
    operator fun invoke(
        studentOrder: StudentOrder = StudentOrder.Name(OrderType.Ascending)
    ): Flow<List<Student>>{
        return repository.getStudents().map { students ->
            when(studentOrder.orderType){
                is OrderType.Ascending -> {
                    when(studentOrder){
                        is StudentOrder.Name -> students.sortedBy { it.name.lowercase() }
                        is StudentOrder.Age -> students.sortedBy { it.age }
                        is StudentOrder.Weight -> students.sortedBy { it.weight }
                        is StudentOrder.Height -> students.sortedBy { it.height }
                        is StudentOrder.ID -> students.sortedBy { it.id }
                    }
                }
                is OrderType.Descending -> {
                    when(studentOrder){
                        is StudentOrder.Name -> students.sortedByDescending { it.name.lowercase() }
                        is StudentOrder.Age -> students.sortedByDescending { it.age }
                        is StudentOrder.Weight -> students.sortedByDescending { it.weight }
                        is StudentOrder.Height -> students.sortedByDescending { it.height }
                        is StudentOrder.ID -> students.sortedByDescending { it.id }
                    }
                }
            }
        }
    }
}