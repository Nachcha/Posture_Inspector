package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidWalkingDataException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.WalkingData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.WalkingDataRepository

class AddWalkingDataList(
    private val repository: WalkingDataRepository
) {
    @Throws(InvalidWalkingDataException::class)
    suspend operator fun invoke(walkingDataList: List<WalkingData>) {
        repository.insertWalkingDataList(walkingDataList)
    }
}