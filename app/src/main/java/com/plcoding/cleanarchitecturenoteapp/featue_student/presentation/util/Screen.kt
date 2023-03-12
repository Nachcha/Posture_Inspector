package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util

sealed class Screen(val route: String){
    object StudentsScreen: Screen("students_screen")
    object AddEditStudentScreen: Screen("add_edit_student_screen")
    object StandStillTestScreen: Screen("stand_still_test_screen")
}
