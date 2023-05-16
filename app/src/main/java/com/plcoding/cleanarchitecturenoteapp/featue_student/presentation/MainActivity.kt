package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.AddEditStudentScreen
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.StandStillTestScreen
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.students.StudentsScreen
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.Screen
import com.plcoding.cleanarchitecturenoteapp.ui.theme.CleanArchitectureNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanArchitectureNoteAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.StudentsScreen.route
                    ) {
                        composable(route = Screen.StudentsScreen.route) {
                            StudentsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditStudentScreen.route +
                                    "?studentId={studentId}&studentGender={studentGender}",
                            arguments = listOf(
                                navArgument(
                                    name = "studentId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "studentGender"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ) {
                            AddEditStudentScreen(navController = navController)
                        }
                        composable(
                            route = Screen.StandStillTestScreen.route +
                                    "?reportId={reportId}&dateTime={dateTime}&studentId={studentId}&reportType={reportType}",
                            arguments = listOf(
                                navArgument(
                                    name = "reportId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "dateTime"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                                navArgument(
                                    name = "studentId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "reportType"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                },
                            )
                        ) {
                            StandStillTestScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

