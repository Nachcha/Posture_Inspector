package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.AddEditStudentViewModel
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.students.StudentsViewModel
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.getBackgroundColor
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.getInverseBackgroundColor

@Composable
fun ReportCard(
    report: Report? = null,
    onClickDelete: () -> Unit,
    addEditStudentViewModel: AddEditStudentViewModel = hiltViewModel()
) {
    val studentGender = addEditStudentViewModel.studentGender.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(vertical = 4.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(Color.Green)
        ) {  }
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(2f)
            .background(getBackgroundColor(studentGender)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "New", style = MaterialTheme.typography.h5)

            IconButton(
                onClick = onClickDelete,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(getInverseBackgroundColor(studentGender)),
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Add new balance report",
                    tint = Color.Black
                )
            }
        }
    }
}