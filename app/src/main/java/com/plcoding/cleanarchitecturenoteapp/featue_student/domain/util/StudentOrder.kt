package com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util

sealed class StudentOrder(val orderType: OrderType){
    class Name(orderType: OrderType): StudentOrder(orderType)
    class Age(orderType: OrderType): StudentOrder(orderType)
    class Weight(orderType: OrderType): StudentOrder(orderType)
    class Height(orderType: OrderType): StudentOrder(orderType)
    class ID(orderType: OrderType): StudentOrder(orderType)

    fun copy(orderType: OrderType): StudentOrder {
        return when(this){
            is Name -> Name(orderType)
            is Age -> Name(orderType)
            is Weight -> Name(orderType)
            is Height -> Name(orderType)
            is ID -> Name(orderType)
        }
    }
}
