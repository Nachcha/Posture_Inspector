package com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository

import com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source.StandStillDataDao
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.StandStillDataRepository
import kotlinx.coroutines.flow.Flow

class StandStillDataRepositoryImpl(
    private val dao: StandStillDataDao
): StandStillDataRepository {

    override fun getStandStillData(studentId: Int): Flow<List<StandStillData>> {
        return dao.getStandStillData(studentId)
    }

    override suspend fun insertStandStillData(standStillData: StandStillData) {
        return dao.insertStandStillData(standStillData)
    }

    override suspend fun deleteStandStillData(studentId: Int) {
        return dao.deleteStandStillData(studentId)
    }
}