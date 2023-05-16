package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test

sealed class StandStillTestEvent {
    object OnStart: StandStillTestEvent()
    object OnSave: StandStillTestEvent()
}