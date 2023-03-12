package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidStandStillDataException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case.StandStillUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StandStillTestViewModel @Inject constructor(
    private val standStillUseCases: StandStillUseCases
): ViewModel() {
    private val _state = mutableStateOf(StandStillTestState())
    val state: State<StandStillTestState> = _state
    private var getStandStillDataListJob: Job? = null
    private var standStillDataRow: StandStillData? = null

    init {
        getStandStillDataList(state.value.studentId)
    }

    fun onEvent(event: StandStillTestEvent) {
        when (event) {
            is StandStillTestEvent.FetchData -> {
                getStandStillDataList(state.value.studentId)
            }
            is StandStillTestEvent.InsertStandStillDataRow -> {
                viewModelScope.launch {
                    standStillUseCases.addStandStillData(standStillDataRow ?: return@launch)
                }
            }
        }
    }

    private fun getStandStillDataList(studentId: Int?){
        if(studentId != null){
            getStandStillDataListJob?.cancel()
            getStandStillDataListJob = standStillUseCases.getStandStillDataList(studentId)
                .onEach { standStillDataList ->
                    _state.value = state.value.copy(
                        standStillDataList = standStillDataList
                    )
                }
                .launchIn(viewModelScope)
        } else {
            _state.value = state.value.copy(
                standStillDataList = emptyList()
            )
            InvalidStandStillDataException("Something went wrong. Student ID is missing.")
        }
    }
}