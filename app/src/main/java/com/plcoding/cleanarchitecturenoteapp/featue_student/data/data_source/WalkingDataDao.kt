package com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.WalkingData
import kotlinx.coroutines.flow.Flow

@Dao
interface WalkingDataDao {
    @Query("SELECT * from walking_data WHERE report_id = :reportId ORDER BY id")
    fun getWalkingData(reportId: Int) : Flow<List<WalkingData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWalkingData(walkingData: WalkingData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWalkingDataList(walkingDataList: List<WalkingData>)

    @Query("DELETE FROM walking_data WHERE  report_id = :reportId")
    suspend fun deleteWalkingData(reportId: Int)
}