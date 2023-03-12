package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student.Companion.genderTypes
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.StudentOrder

@Composable
fun GenderSection(
    modifier: Modifier = Modifier,
    gender: String = "",
    onGenderChanged: (String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Male",
                checked = gender == "Male",
                onCheck = { onGenderChanged("Male") },
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Female",
                checked = gender == "Female",
                onCheck = { onGenderChanged("Female") }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}