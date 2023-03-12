package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.repository

import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.WalkingData
import kotlinx.coroutines.flow.Flow

interface WalkingDataRepository {
    fun getWalkingData(reportId: Int): Flow<List<WalkingData>>

    suspend fun insertWalkingData(walkingData: WalkingData)

    suspend fun deleteWalkingData(reportId: Int)
}