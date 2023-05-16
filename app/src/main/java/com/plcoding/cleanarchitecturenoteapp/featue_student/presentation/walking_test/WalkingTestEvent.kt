package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.walking_test

sealed class WalkingTestEvent {
    object OnStart: WalkingTestEvent()
    object OnSave: WalkingTestEvent()
}