package com.plcoding.cleanarchitecturenoteapp.featue_student.data.repository

import com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source.WalkingDataDao
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.WalkingData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository.WalkingDataRepository
import kotlinx.coroutines.flow.Flow

class WalkingDataRepositoryImpl(
    private val dao: WalkingDataDao
): WalkingDataRepository {
    override fun getWalkingData(reportId: Int): Flow<List<WalkingData>> {
        return dao.getWalkingData(reportId)
    }

    override suspend fun insertWalkingData(walkingData: WalkingData) {
        return dao.insertWalkingData(walkingData)
    }

    override suspend fun insertWalkingDataList(WalkingDataList: List<WalkingData>) {
        return dao.insertWalkingDataList(WalkingDataList)
    }

    override suspend fun deleteWalkingData(reportId: Int) {
        return dao.deleteWalkingData(reportId)
    }
}