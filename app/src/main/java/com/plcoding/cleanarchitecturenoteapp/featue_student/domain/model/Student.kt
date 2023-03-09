package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    val name : String,
    val age : Int,
    val weight : Int,
    val height : Int,
    @PrimaryKey val id: Int? = null
)

class InvalidStudentException(message: String): Exception(message)
