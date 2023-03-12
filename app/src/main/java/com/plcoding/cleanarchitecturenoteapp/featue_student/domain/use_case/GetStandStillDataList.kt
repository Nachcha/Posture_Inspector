package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StandStillDataRepository
import kotlinx.coroutines.flow.Flow

class GetStandStillDataList(
    private val repository: StandStillDataRepository
) {
    operator fun invoke(reportId: Int): Flow<List<StandStillData>> {
        return repository.getStandStillData(reportId)
    }
}