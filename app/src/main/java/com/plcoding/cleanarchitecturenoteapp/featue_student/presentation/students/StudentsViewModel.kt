package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.students

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case.StudentUseCases
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.OrderType
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.StudentOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentsViewModel @Inject constructor(
    private val studentUseCases: StudentUseCases
) : ViewModel() {

    private val _state = mutableStateOf(StudentsState())
    val state: State<StudentsState> = _state
    private var recentlyDeletedStudent: Student? = null
    private var getStudentsJob: Job? = null

    init {
        getStudents(StudentOrder.Name(OrderType.Ascending))
    }

    fun onEvent(event: StudentsEvent) {
        when (event) {
            is StudentsEvent.Order -> {
                if (state.value.studentOrder::class == event.studentOrder::class &&
                    state.value.studentOrder.orderType == event.studentOrder.orderType
                ) {
                    return
                }
                getStudents(event.studentOrder)
            }
            is StudentsEvent.DeleteStudent -> {
                viewModelScope.launch {
                    studentUseCases.deleteStudent(event.student)
                    recentlyDeletedStudent = event.student
                }
            }
            is StudentsEvent.RestoreStudent -> {
                viewModelScope.launch {
                    studentUseCases.addStudent(recentlyDeletedStudent ?: return@launch)
                    recentlyDeletedStudent = null
                }
            }
            is StudentsEvent.ToggleOrderSelection -> {
                _state.value = state.value.copy(
                    isStudentsSectionVisible = !state.value.isStudentsSectionVisible
                )
            }
        }
    }

    private fun getStudents(studentOrder: StudentOrder){
        getStudentsJob?.cancel()
        getStudentsJob = studentUseCases.getStudents(studentOrder)
            .onEach { students ->
                _state.value = state.value.copy(
                    students = students,
                    studentOrder = studentOrder
                )
            }
            .launchIn(viewModelScope)
    }
}