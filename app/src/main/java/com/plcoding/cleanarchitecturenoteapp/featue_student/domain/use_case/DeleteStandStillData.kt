package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StandStillDataRepository

class DeleteStandStillData(
    private val repository: StandStillDataRepository
) {
    suspend operator fun invoke(reportId: Int){
        repository.deleteStandStillData(reportId)
    }
}