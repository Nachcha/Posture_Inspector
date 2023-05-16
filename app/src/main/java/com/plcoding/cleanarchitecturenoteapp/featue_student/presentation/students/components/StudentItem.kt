package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.students.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.ui.theme.FemaleColor
import com.plcoding.cleanarchitecturenoteapp.ui.theme.MaleColor

@Composable
fun StudentItem(
    student: Student,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 10.dp,
    cutCornerSize: Dp = 30.dp,
) {
    Box(
        modifier = modifier
    ) {
        Canvas(modifier = Modifier.matchParentSize()){
            val clipPath = Path().apply {
                lineTo(size.width-cutCornerSize.toPx(),0f)
                lineTo(size.width,cutCornerSize.toPx())
                lineTo(size.width,size.height)
                lineTo(0f,size.height)
                close()
            }

            clipPath(clipPath){
                drawRoundRect(
                    color = if (student.gender == "Male") MaleColor else FemaleColor,
                    size = size,
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
                drawRoundRect(
                    color = if (student.gender == "Male") FemaleColor else MaleColor,
                    topLeft = Offset(size.width-cutCornerSize.toPx(),-100f),
                    size = Size(cutCornerSize.toPx() + 100f, cutCornerSize.toPx() + 100f),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = student.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Column {
                    Text(
                        text = "Age : ${student.age}, Gender : ${student.gender},",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Weight : ${student.weight}, Height : ${student.weight}",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}