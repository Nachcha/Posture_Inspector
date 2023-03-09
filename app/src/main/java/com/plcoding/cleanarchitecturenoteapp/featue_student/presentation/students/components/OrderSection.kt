package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.students.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.StudentOrder

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    studentOrder: StudentOrder = StudentOrder.Name(OrderType.Ascending),
    onOrderChanged: (StudentOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Name",
                checked = studentOrder is StudentOrder.Name,
                onCheck = { onOrderChanged(StudentOrder.Name(studentOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Age",
                checked = studentOrder is StudentOrder.Age,
                onCheck = { onOrderChanged(StudentOrder.Name(studentOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Weight",
                checked = studentOrder is StudentOrder.Weight,
                onCheck = { onOrderChanged(StudentOrder.Name(studentOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Height",
                checked = studentOrder is StudentOrder.Height,
                onCheck = { onOrderChanged(StudentOrder.Name(studentOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Ascending",
                checked = studentOrder.orderType is OrderType.Ascending,
                onCheck = { onOrderChanged(studentOrder.copy(OrderType.Ascending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioButton(
                text = "Descending",
                checked = studentOrder.orderType is OrderType.Descending,
                onCheck = { onOrderChanged(studentOrder.copy(OrderType.Descending)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}