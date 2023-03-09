package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.components.TransparentHintTextFeild
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


fun getBackgroundColor(gender: String): Color {
    return if (gender == "Female") {
        Color(255, 192, 203, 1)
    } else {
        Color(145, 210, 255, 1)
    }
}

@Composable
fun AddEditStudentScreen(
    navController: NavController,
    studentGender: String,
    viewModel: AddEditStudentViewModel = hiltViewModel()
) {
    val nameState = viewModel.studentName.value
    val ageState = viewModel.studentAge.value
    val weightState = viewModel.studentWeight.value
    val heightState = viewModel.studentHeight.value

    val scaffoldState = rememberScaffoldState()

    val studentBackgroundAnimatable = remember {
        Animatable(
            getBackgroundColor(studentGender)
        )
    }
    
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is AddEditStudentViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditStudentViewModel.UiEvent.SaveStudent -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditStudentEvent.SaveStudent)
                },
                backgroundColor = MaterialTheme.colors.primary,
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save student")
            }
        }, scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(studentBackgroundAnimatable.value)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextFeild(
                text = nameState.text,
                hint = nameState.hint,
                onValueChange = { viewModel.onEvent(AddEditStudentEvent.EnteredName(it)) },
                onFocusChange = { viewModel.onEvent(AddEditStudentEvent.ChangeNameFocus(it)) },
                isHintVisible = nameState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextFeild(
                text = ageState.value.toString(),
                hint = ageState.hint,
                onValueChange = { viewModel.onEvent(AddEditStudentEvent.EnteredAge(it.toInt())) },
                onFocusChange = { viewModel.onEvent(AddEditStudentEvent.ChangeAgeFocus(it)) },
                isHintVisible = ageState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextFeild(
                text = weightState.value.toString(),
                hint = weightState.hint,
                onValueChange = { viewModel.onEvent(AddEditStudentEvent.EnteredWeight(it.toInt())) },
                onFocusChange = { viewModel.onEvent(AddEditStudentEvent.ChangeWeightFocus(it)) },
                isHintVisible = weightState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextFeild(
                text = heightState.value.toString(),
                hint = heightState.hint,
                onValueChange = { viewModel.onEvent(AddEditStudentEvent.EnteredHeight(it.toInt())) },
                onFocusChange = { viewModel.onEvent(AddEditStudentEvent.ChangeHeightFocus(it)) },
                isHintVisible = heightState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}