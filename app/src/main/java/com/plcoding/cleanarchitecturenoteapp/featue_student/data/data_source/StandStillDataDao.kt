package com.plcoding.cleanarchitecturenoteapp.featue_student.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import kotlinx.coroutines.flow.Flow

@Dao
interface StandStillDataDao {
    @Query("SELECT * from stand_still_data WHERE report_id = :reportId ORDER BY time_stamp ASC")
    fun getStandStillData(reportId: Int) : Flow<List<StandStillData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStandStillData(standStillData: StandStillData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStandStillDataList(standStillDataList: List<StandStillData>)

    @Query("DELETE FROM stand_still_data WHERE  report_id = :reportId")
    suspend fun deleteStandStillData(reportId: Int)
}