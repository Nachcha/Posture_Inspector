package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test

import androidx.compose.foundation.layout.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.components.HeatMap

@Composable
fun StandStillTestScreen(
    navController: NavController,
    viewModel: StandStillTestViewModel = hiltViewModel(),
) {
    val testRecord: Array<Int> =
        arrayOf(100, 70, 70, 60, 40, 20, 10, 5, 30, 60, 80, 39, 72, 67, 47, 20)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HeatMap(
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

