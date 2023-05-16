package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidStandStillDataException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StandStillDataRepository

class AddStandStillDataList (
    private val repository: StandStillDataRepository
) {
    @Throws(InvalidStandStillDataException::class)
    suspend operator fun invoke(standStillDataList: List<StandStillData>) {
        repository.insertStandStillDataList(standStillDataList)
    }
}