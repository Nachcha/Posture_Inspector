package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.WalkingData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.WalkingDataRepository
import kotlinx.coroutines.flow.Flow

class GetWalkingDataList (
    private val repository: WalkingDataRepository
) {
    operator fun invoke(reportId: Int): Flow<List<WalkingData>> {
        return repository.getWalkingData(reportId)
    }
}