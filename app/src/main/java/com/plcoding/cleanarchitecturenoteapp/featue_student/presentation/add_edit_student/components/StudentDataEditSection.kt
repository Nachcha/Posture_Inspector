package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.AddEditStudentEvent
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.AddEditStudentViewModel
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.getBackgroundColor
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.getInverseBackgroundColor
import com.plcoding.cleanarchitecturenoteapp.ui.theme.FemaleColor
import com.plcoding.cleanarchitecturenoteapp.ui.theme.MaleColor

@Composable
fun StudentDataEditSection(
    modifier: Modifier,
    viewModel: AddEditStudentViewModel = hiltViewModel()
) {
    val nameState = viewModel.studentName.value
    val ageState = viewModel.studentAge.value
    val weightState = viewModel.studentWeight.value
    val heightState = viewModel.studentHeight.value
    val studentGender = viewModel.studentGender.value

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(getInverseBackgroundColor(studentGender)),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(getBackgroundColor(studentGender))
                    .padding(20.dp)
            ) {

                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextFeild(
                    text = nameState.text,
                    hint = nameState.hint,
                    name = "Name : ",
                    onValueChange = { viewModel.onEvent(AddEditStudentEvent.EnteredName(it)) },
                    onFocusChange = { viewModel.onEvent(AddEditStudentEvent.ChangeNameFocus(it)) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5,
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextFeild(
                    text = ageState.value.toString(),
                    hint = ageState.hint,
                    name = "Age (years) : ",
                    onValueChange = { viewModel.onEvent(AddEditStudentEvent.EnteredAge(it.toInt())) },
                    onFocusChange = { viewModel.onEvent(AddEditStudentEvent.ChangeAgeFocus(it)) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextFeild(
                    text = weightState.value.toString(),
                    hint = weightState.hint,
                    name = "Weight (Kg) : ",
                    onValueChange = { viewModel.onEvent(AddEditStudentEvent.EnteredWeight(it.toInt())) },
                    onFocusChange = { viewModel.onEvent(AddEditStudentEvent.ChangeWeightFocus(it)) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextFeild(
                    text = heightState.value.toString(),
                    hint = heightState.hint,
                    name = "Height (cm) : ",
                    onValueChange = { viewModel.onEvent(AddEditStudentEvent.EnteredHeight(it.toInt())) },
                    onFocusChange = { viewModel.onEvent(AddEditStudentEvent.ChangeHeightFocus(it)) },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                GenderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    gender = studentGender,
                    onGenderChanged = {
                        viewModel.onEvent(AddEditStudentEvent.GenderChanged(it))
                    }
                )
            }
            FloatingActionButton(
                modifier = Modifier
                    .offset((-20).dp,(-20).dp),
                onClick = {
                    viewModel.onEvent(AddEditStudentEvent.SaveStudent)
                },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save student")
            }
        }
    }
}