package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import kotlinx.coroutines.flow.Flow

interface StandStillDataRepository {

    fun getStandStillData(reportId: Int): Flow<List<StandStillData>>

    suspend fun insertStandStillData(standStillData: StandStillData)

    suspend fun insertStandStillDataList(standStillDataList: List<StandStillData>)

    suspend fun deleteStandStillData(reportId: Int)
}