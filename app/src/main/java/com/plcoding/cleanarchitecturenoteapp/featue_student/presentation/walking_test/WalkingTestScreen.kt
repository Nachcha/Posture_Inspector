package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.walking_test


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.shared_components.AnimatedCounter
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.components.HeatMapWalking

@Composable
fun WalkingTestScreen(
    navController: NavController,
    viewModel: WalkingTestViewModel = hiltViewModel(),
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeatMapWalking(
                    modifier = Modifier.fillMaxWidth(),
                    dataObject = viewModel.currentRecord.value
                )
            }
            Row(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedCounter(
                    count = viewModel.timerCount.value,
                    textStyle = MaterialTheme.typography.h1,
                    maxCount = viewModel.maxTimerCount.value,
                    modifier = Modifier
                        .width(180.dp)
                        .height(180.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom,
            ) {
                Button(
                    modifier = Modifier
                        .width(100.dp)
                        .background(Color.White),
                    enabled = viewModel.startButtonEnabled.value,
                    onClick = {
                        viewModel.onEvent(WalkingTestEvent.OnStart)
                    }) {
                    Text(style = TextStyle(color = Color.Black), text = viewModel.startButtonText.value)
                }
                Button(
                    modifier = Modifier
                        .width(100.dp)
                        .background(Color.White),
                    enabled = viewModel.saveButtonEnabled.value,
                    onClick = {
                        viewModel.onEvent(WalkingTestEvent.OnSave)
                    }) {
                    Text(style = TextStyle(color = Color.Black), text = viewModel.saveButtonText.value)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(20.dp)
                    .border(1.dp, Color.White),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        color = Color.Yellow,
                        fontSize = 20.sp
                    ),
                    text = viewModel.message.value,
                )
            }
        }
    }
}