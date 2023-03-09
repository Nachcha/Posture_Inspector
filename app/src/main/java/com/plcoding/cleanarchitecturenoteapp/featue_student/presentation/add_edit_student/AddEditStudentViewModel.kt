package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidStudentException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Student
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case.StudentUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditStudentViewModel @Inject constructor(
    private val studentUseCases: StudentUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _studentName = mutableStateOf(StudentTextFieldState(
        hint = "Name"
    ))
    val studentName: State<StudentTextFieldState> = _studentName

    private val _studentAge = mutableStateOf(StudentIntFeildState(
        hint = "Age"
    ))
    val studentAge: State<StudentIntFeildState> = _studentAge

    private val _studentWeight = mutableStateOf(StudentIntFeildState(
        hint = "Weight"
    ))
    val studentWeight: State<StudentIntFeildState> = _studentWeight

    private val _studentHeight = mutableStateOf(StudentIntFeildState(
        hint = "Height"
    ))
    val studentHeight: State<StudentIntFeildState> = _studentHeight

    private val _studentGender = mutableStateOf("Male")
    val studentGender: State<String> = _studentGender

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentStudentId: Int? = null

    init {
        savedStateHandle.get<Int>("studentId")?.let { studentId ->
            if (studentId != -1){
                viewModelScope.launch {
                    studentUseCases.getStudent(studentId)?.also { student ->
                        currentStudentId = student.id
                        _studentName.value = studentName.value.copy(
                            text = student.name,
                            isHintVisible =  false
                        )
                        _studentAge.value = studentAge.value.copy(
                            value = student.age,
                            isHintVisible =  false
                        )
                        _studentWeight.value = studentWeight.value.copy(
                            value = student.weight,
                            isHintVisible =  false
                        )
                        _studentHeight.value = studentHeight.value.copy(
                            value = student.height,
                            isHintVisible =  false
                        )
                        _studentGender.value = studentGender.value
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditStudentEvent){
        when(event){
            is AddEditStudentEvent.EnteredName -> {
                _studentName.value = studentName.value.copy(
                    text = event.value
                )
            }
            is AddEditStudentEvent.ChangeNameFocus -> {
                _studentName.value = studentName.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }

            is AddEditStudentEvent.EnteredAge -> {
                _studentAge.value = studentAge.value.copy(
                    value = event.value
                )
            }
            is AddEditStudentEvent.ChangeAgeFocus -> {
                _studentAge.value = studentAge.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }

            is AddEditStudentEvent.EnteredWeight -> {
                _studentWeight.value = studentWeight.value.copy(
                    value = event.value
                )
            }
            is AddEditStudentEvent.ChangeWeightFocus -> {
                _studentWeight.value = studentWeight.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }

            is AddEditStudentEvent.EnteredHeight -> {
                _studentHeight.value = studentHeight.value.copy(
                    value = event.value
                )
            }
            is AddEditStudentEvent.ChangeHeightFocus -> {
                _studentHeight.value = studentHeight.value.copy(
                    isHintVisible = !event.focusState.isFocused
                )
            }

            AddEditStudentEvent.SaveStudent -> {
                viewModelScope.launch {
                    try {
                        studentUseCases.addStudent(
                            Student(
                                name = studentName.value.text,
                                age = studentAge.value.value,
                                weight = studentWeight.value.value,
                                height = studentHeight.value.value,
                                id = currentStudentId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveStudent)
                    } catch (e: InvalidStudentException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Couldn't save student."
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveStudent: UiEvent()
    }
}