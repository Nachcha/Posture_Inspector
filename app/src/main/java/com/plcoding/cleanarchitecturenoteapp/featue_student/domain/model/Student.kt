package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student(
    val name: String,
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: String,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val genderTypes = listOf("Male", "Female")
    }
}

class InvalidStudentException(message: String) : Exception(message)
