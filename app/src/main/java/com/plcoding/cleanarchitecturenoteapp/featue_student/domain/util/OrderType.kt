package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util

sealed class OrderType{
    object Ascending: OrderType()
    object Descending: OrderType()
}
