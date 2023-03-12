package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidStandStillDataException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidStudentException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StandStillDataRepository

class AddStandStillData(
    private val repository: StandStillDataRepository
) {
    @Throws(InvalidStandStillDataException::class)
    suspend operator fun invoke(standStillData: StandStillData) {
//        if (standStillData.student_id == null){
//            throw InvalidStandStillDataException("Student ID should not be null.")
//        }
        repository.insertStandStillData(standStillData)
    }
}