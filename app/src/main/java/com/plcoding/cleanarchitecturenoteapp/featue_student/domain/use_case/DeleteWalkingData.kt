package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidWalkingDataException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.WalkingDataRepository

class DeleteWalkingData (
    private val repository: WalkingDataRepository
) {
    @Throws(InvalidWalkingDataException::class)
    suspend operator fun invoke(reportId: Int) {
        repository.deleteWalkingData(reportId)
    }
}