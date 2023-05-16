package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.AddDeleteReportsEvent
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.AddEditStudentViewModel
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.Screen
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.getBackgroundColor
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.getInverseBackgroundColor

@Composable
fun AddViewDeleteReportSection(
    navController: NavController,
    modifier: Modifier,
    student: Student,
    viewModel: AddEditStudentViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(getBackgroundColor(student.gender))
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Balance",
                    style = MaterialTheme.typography.h5,
                )
                IconButton(
                    onClick = {
                        if (student.id != null) {
                            viewModel.onReportEvent(
                                AddDeleteReportsEvent.SaveBalanceReport(
                                    Report(
                                        report_type = "Balance",
                                        student_id = student.id,
                                        date_time = "New",
                                    )
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(getInverseBackgroundColor(student.gender)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new balance report",
                        tint = Color.Black
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(state.balanceReports) { report ->
                    Log.d("ReportIDChecking", "AddViewDeleteReportSection: ${report.id}")
                    ReportCard(
                        modifier = Modifier.clickable {
                            navController.navigate(
                                Screen.StandStillTestScreen.route +
                                        "?reportId=${report.id}&dateTime=${report.date_time}&studentId=${report.student_id}&reportType=${report.report_type}"
                            )
                        },
                        report = report,
                        onClickDelete = {
                            viewModel.onReportEvent(
                                AddDeleteReportsEvent.DeleteReport(
                                    report
                                )
                            )
                        }
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(getBackgroundColor(student.gender))
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Walking",
                    style = MaterialTheme.typography.h5,
                )
                IconButton(
                    onClick = {
                        if (student.id != null) {
                            viewModel.onReportEvent(
                                AddDeleteReportsEvent.SaveWalkingReport(
                                    Report(
                                        report_type = "Walking",
                                        student_id = student.id,
                                        date_time = "New",
                                    )
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(getInverseBackgroundColor(student.gender)),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new balance report",
                        tint = Color.Black
                    )
                }
            }
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.walkingReports) { report ->
                    ReportCard(
                        modifier = Modifier.clickable {
                            navController.navigate(
                                Screen.StandStillTestScreen.route +
                                        "?reportId=${report.id}&dateTime=${report.date_time}&studentId=${report.student_id}&reportType=${report.report_type}"
                            )
                        },
                        report = report,
                        onClickDelete = {
                            viewModel.onReportEvent(
                                AddDeleteReportsEvent.DeleteReport(
                                    report
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}