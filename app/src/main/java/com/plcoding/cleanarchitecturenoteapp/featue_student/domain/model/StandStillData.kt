package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "stand_still_data",
    foreignKeys = [ForeignKey(
        entity = Report::class,
        childColumns = ["report_id"],
        parentColumns = ["id"]
    )])
data class StandStillData(
    val l0 : Int,
    val l1 : Int,
    val l2 : Int,
    val l4 : Int,
    val l5 : Int,
    val l6 : Int,
    val l7 : Int,
    val l8 : Int,
    val l9 : Int,
    val l10 : Int,
    val l11 : Int,
    val l12 : Int,
    val l13 : Int,
    val l14 : Int,
    val l15 : Int,
    val r0 : Int,
    val r1 : Int,
    val r2 : Int,
    val r4 : Int,
    val r5 : Int,
    val r6 : Int,
    val r7 : Int,
    val r8 : Int,
    val r9 : Int,
    val r10 : Int,
    val r11 : Int,
    val r12 : Int,
    val r13 : Int,
    val r14 : Int,
    val r15 : Int,
    val time_stamp : Int,
    val report_id : Int,
    @PrimaryKey val id: Int? = null,
    )

class InvalidStandStillDataException(message: String): Exception(message)
