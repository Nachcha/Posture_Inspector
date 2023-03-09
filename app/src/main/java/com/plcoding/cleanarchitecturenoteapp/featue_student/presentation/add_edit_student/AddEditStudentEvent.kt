package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.add_edit_student

import androidx.compose.ui.focus.FocusState

sealed class AddEditStudentEvent{
    data class EnteredName(val value: String): AddEditStudentEvent()
    data class ChangeNameFocus(val focusState: FocusState): AddEditStudentEvent()

    data class EnteredAge(val value: Int): AddEditStudentEvent()
    data class ChangeAgeFocus(val focusState: FocusState): AddEditStudentEvent()

    data class EnteredWeight(val value: Int): AddEditStudentEvent()
    data class ChangeWeightFocus(val focusState: FocusState): AddEditStudentEvent()

    data class EnteredHeight(val value: Int): AddEditStudentEvent()
    data class ChangeHeightFocus(val focusState: FocusState): AddEditStudentEvent()

    object SaveStudent: AddEditStudentEvent()
}
