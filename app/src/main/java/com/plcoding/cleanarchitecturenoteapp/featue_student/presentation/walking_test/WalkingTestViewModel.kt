package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.walking_test

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.core.servises.BluetoothService
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidWalkingDataException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.WalkingData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case.ReportUseCases
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case.WalkingUseCases
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.WalkingDataDTO
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.walking_test.utils.*
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.PointsL
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.PointsR
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.walking_test.utils.HeatMapDataObjectWalking
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WalkingTestViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext val context: Context,
    private val walkingUseCases: WalkingUseCases,
    private val reportUseCases: ReportUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(WalkingTestState())
    val state: State<WalkingTestState> = _state

    private var getWalkingDataListJob: Job? = null

    private val _timerCount = mutableStateOf(0)
    val timerCount: State<Int> = _timerCount

    private val _maxTimerCount = mutableStateOf(20)
    val maxTimerCount: State<Int> = _maxTimerCount

    private val _sensorL =
        mutableStateOf(arrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f))
    val sensorL: State<Array<Float>> = _sensorL

    private val _sensorR =
        mutableStateOf(arrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f))
    val sensorR: State<Array<Float>> = _sensorR

    private val _currentRecord =
        mutableStateOf(HeatMapDataObjectWalking(sensorL.value, sensorR.value, emptyList()))
    val currentRecord: State<HeatMapDataObjectWalking> = _currentRecord

    private val _message = mutableStateOf("")
    val message: State<String> = _message

    private val _startButtonEnabled = mutableStateOf(true)
    val startButtonEnabled: State<Boolean> = _startButtonEnabled

    private val _saveButtonEnabled = mutableStateOf(false)
    val saveButtonEnabled: State<Boolean> = _saveButtonEnabled

    private val _saveButtonText = mutableStateOf("Save")
    val saveButtonText: State<String> = _saveButtonText

    private val _startButtonText = mutableStateOf("Start")
    val startButtonText: State<String> = _startButtonText

    private val _screenMode = mutableStateOf(0)
    val screenMode: State<Int> = _screenMode

    private var records = mutableListOf<WalkingDataDTO>()
    private lateinit var timer: Timer
    private var deciSecondCount: Int = 0
    private var replayIndex: Int = 0

    // region Bluetooth
    private val bluetoothSerial = BluetoothService(object : BluetoothService.Listener {
        override fun onMessageReceived(device: String, message: String) {
            // Handle incoming messages from the ESP32 devices
            when (device) {
                "Left_foot" -> {
                    if (message.trim().isNotBlank()) {
//                        Log.d("BlueToothL", message)
                        val valArray: Array<String> = message.split(",").toTypedArray()
                        if (valArray.isNotEmpty()) {
                            val valFloatArray: Array<Float> =
                                valArray.map { it.toFloat() }.toTypedArray()
                            viewModelScope.launch {
                                _sensorL.value = valFloatArray
                            }
                        }
                    }
                }
                "Right_foot" -> {
                    if (message.trim().isNotBlank()) {
//                        Log.d("BlueToothR", message)
                        val valArray: Array<String> = message.split(",").toTypedArray()
                        if (valArray.isNotEmpty()) {
                            val valFloatArray: Array<Float> =
                                valArray.map { it.toFloat() }.toTypedArray()
                            viewModelScope.launch {
                                _sensorR.value = valFloatArray
                            }
                        }
                    }
                }
            }

        }

        override fun onError(errorMessage: String) {
            // Handle errors that occur while connecting to or communicating with the ESP32 devices
            Log.e("BlueTooth", errorMessage)
        }
    }, context)
    // endregion

    init {
        savedStateHandle.get<Int>("reportId")?.let { reportId ->
//            Log.d("ReportData", "reportId: $reportId")
            if (reportId != -1) {
                _state.value = state.value.copy(
                    reportId = reportId,
                )
                getWalkingDataList(state.value.reportId)
            }
        }

        savedStateHandle.get<String>("dateTime")?.let { dateTime ->
//            Log.d("ReportData", "reportId: $dateTime")
            if (dateTime != "") {
                _state.value = state.value.copy(
                    dateTime = dateTime,
                )
            }
        }

        savedStateHandle.get<Int>("studentId")?.let { studentId ->
//            Log.d("ReportData", "reportId: $studentId")
            if (studentId != -1) {
                _state.value = state.value.copy(
                    studentId = studentId,
                )
            }
        }

        savedStateHandle.get<String>("reportType")?.let { reportType ->
//            Log.d("ReportData", "reportId: $reportType")
            if (reportType != "") {
                _state.value = state.value.copy(
                    reportType = reportType,
                )
            }
        }

        if (state.value.dateTime!!.trim() != "New") {
            viewModelScope.launch {
                _screenMode.value = 1
                _saveButtonText.value = "Pause"
                _startButtonText.value = "Play"
                _message.value = message_rp1
            }
        } else {
            viewModelScope.launch() {
                _message.value = message_rd1
                _screenMode.value = 0
                _saveButtonText.value = "Save"
                _startButtonText.value = "Start"
            }
        }
    }

    fun onEvent(event: WalkingTestEvent) {
        when (event) {
            is WalkingTestEvent.OnStart -> {
                if (screenMode.value > 0) {
                    startReplay()
                } else {
                    startRecording()
                }
            }
            is WalkingTestEvent.OnSave -> {
                if (screenMode.value > 0) {
                    stopReplay()
                } else {
                    saveRecordList()
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimerRecord()
    }

    // region Read Recorded Data
    private fun getWalkingDataList(reportId: Int?) {
        if (reportId != null) {
            getWalkingDataListJob?.cancel()
            getWalkingDataListJob = walkingUseCases.getWalkingDataList(reportId)
                .onEach { walkingDataList ->
                    _state.value = state.value.copy(
                        walkingDataList = walkingDataList
                    )
//                    Log.d("GetWalkingDataList", "getWalkingDataList: ${state.value.walkingDataList.size}}")
                }
                .launchIn(viewModelScope)
        } else {
            _state.value = state.value.copy(
                walkingDataList = emptyList()
            )
            throw InvalidWalkingDataException("Something went wrong. Student ID is missing.")
        }
    }
    // endregion

    // region Record Data
    private fun startRecording() {
        bluetoothSerial.connectToDevice()
        viewModelScope.launch {
            _timerCount.value = 20
        }
        startTimerRecord()
    }

    private fun stopRecording() {
        bluetoothSerial.disconnectFromDevices()
        stopTimerRecord()
    }

    private fun recordsToWalkingDataList() {
        val standDataList = mutableListOf<WalkingData>()
        records.forEach { record ->
            standDataList.add(
                WalkingData(
                    l0 = record.l0.toInt(),
                    l1 = record.l1.toInt(),
                    l2 = record.l2.toInt(),
                    l3 = record.l3.toInt(),
                    l4 = record.l4.toInt(),
                    l5 = record.l5.toInt(),
                    l6 = record.l6.toInt(),
                    l7 = record.l7.toInt(),
                    l8 = record.l8.toInt(),
                    l9 = record.l9.toInt(),
                    l10 = record.l10.toInt(),
                    l11 = record.l11.toInt(),
                    l12 = record.l12.toInt(),
                    l13 = record.l13.toInt(),
                    l14 = record.l14.toInt(),
                    l15 = record.l15.toInt(),
                    r0 = record.r0.toInt(),
                    r1 = record.r1.toInt(),
                    r2 = record.r2.toInt(),
                    r3 = record.r3.toInt(),
                    r4 = record.r4.toInt(),
                    r5 = record.r5.toInt(),
                    r6 = record.r6.toInt(),
                    r7 = record.r7.toInt(),
                    r8 = record.r8.toInt(),
                    r9 = record.r9.toInt(),
                    r10 = record.r10.toInt(),
                    r11 = record.r11.toInt(),
                    r12 = record.r12.toInt(),
                    r13 = record.r13.toInt(),
                    r14 = record.r14.toInt(),
                    r15 = record.r15.toInt(),
                    time_stamp = record.time_stamp,
                    report_id = state.value.reportId!!
                )
            )
        }
        _state.value = state.value.copy(
            walkingDataList = standDataList
        )
    }

    private fun startTimerRecord() {
        _startButtonEnabled.value = false
        timer = Timer()
        viewModelScope.launch {
            _message.value = message_rd2
        }
        val timerTask = object : TimerTask() {
            override fun run() {
                deciSecondCount++
                if (deciSecondCount == 10) {
                    deciSecondCount = 0
                    decrementTimer()
                }

                if (timerCount.value <= 10) {
                    val newRecord = WalkingDataDTO(
                        sensorL.value[0],
                        sensorL.value[1],
                        sensorL.value[2],
                        sensorL.value[3],
                        sensorL.value[4],
                        sensorL.value[5],
                        sensorL.value[6],
                        sensorL.value[7],
                        sensorL.value[8],
                        sensorL.value[9],
                        sensorL.value[10],
                        sensorL.value[11],
                        sensorL.value[12],
                        sensorL.value[13],
                        sensorL.value[14],
                        sensorL.value[15],
                        sensorR.value[0],
                        sensorR.value[1],
                        sensorR.value[2],
                        sensorR.value[3],
                        sensorR.value[4],
                        sensorR.value[5],
                        sensorR.value[6],
                        sensorR.value[7],
                        sensorR.value[8],
                        sensorR.value[9],
                        sensorR.value[10],
                        sensorR.value[11],
                        sensorR.value[12],
                        sensorR.value[13],
                        sensorR.value[14],
                        sensorR.value[15],
                        System.currentTimeMillis()
                    )

                    records.add(
                        newRecord
                    )

                    var centerTmp = calculateWeightedCenter(sensorL.value, sensorR.value)
                    var centerPointsTmp = currentRecord.value.centerPoints

                    centerPointsTmp = if (centerTmp != null) {
                        if (!centerTmp.x.isNaN() && !centerTmp.y.isNaN()) {
                            centerPointsTmp.plus(centerTmp)
                        } else {
                            centerPointsTmp
                        }
                    } else {
                        centerPointsTmp
                    }
                    viewModelScope.launch {
                        _currentRecord.value = currentRecord.value.copy(
                            sensorL = sensorL.value,
                            sensorR = sensorR.value,
                            centerPoints = centerPointsTmp
                        )
                    }
                    if (timerCount.value == 0) {
                        stopRecording()
                        _saveButtonEnabled.value = true
                        viewModelScope.launch {
                            _message.value = message_rd3
                        }
                    }
                } else {
                    viewModelScope.launch {
                        _currentRecord.value = currentRecord.value.copy(
                            sensorL = sensorL.value,
                            sensorR = sensorR.value,
                            centerPoints = currentRecord.value.centerPoints
                        )
                    }
                }
            }
        }

        // Start the timer after a delay of 1 second and repeat every 2 seconds
        timer.schedule(timerTask, 0, 100)
    }

    private fun stopTimerRecord() {
        if (::timer.isInitialized) {
            timer.cancel()
        }
    }
    // endregion

    // region Replay Controls
    private fun startReplay() {
        if(replayIndex <= 0) {
            viewModelScope.launch {
                _timerCount.value = 0
                _maxTimerCount.value = 10
            }
        }
        startTimerReplay()
    }

    private fun stopReplay() {
        stopTimerReplay()
        viewModelScope.launch {
            _startButtonEnabled.value = true
            _saveButtonEnabled.value = false
        }
    }

    private fun startTimerReplay() {
        timer = Timer()
        viewModelScope.launch {
            _message.value = message_rp2
            _startButtonEnabled.value = false
            _saveButtonEnabled.value = true
        }
        val timerTask = object : TimerTask() {
            override fun run() {
                deciSecondCount++
                replayIndex++
                if (deciSecondCount == 10) {
                    deciSecondCount = 0
                    incrementTimer()
                }

                _sensorL.value = arrayOf(
                    state.value.walkingDataList[replayIndex].l0.toFloat(),
                    state.value.walkingDataList[replayIndex].l1.toFloat(),
                    state.value.walkingDataList[replayIndex].l2.toFloat(),
                    state.value.walkingDataList[replayIndex].l3.toFloat(),
                    state.value.walkingDataList[replayIndex].l4.toFloat(),
                    state.value.walkingDataList[replayIndex].l5.toFloat(),
                    state.value.walkingDataList[replayIndex].l6.toFloat(),
                    state.value.walkingDataList[replayIndex].l7.toFloat(),
                    state.value.walkingDataList[replayIndex].l8.toFloat(),
                    state.value.walkingDataList[replayIndex].l9.toFloat(),
                    state.value.walkingDataList[replayIndex].l10.toFloat(),
                    state.value.walkingDataList[replayIndex].l11.toFloat(),
                    state.value.walkingDataList[replayIndex].l12.toFloat(),
                    state.value.walkingDataList[replayIndex].l13.toFloat(),
                    state.value.walkingDataList[replayIndex].l14.toFloat(),
                    state.value.walkingDataList[replayIndex].l15.toFloat()
                )

                _sensorR.value = arrayOf(
                    state.value.walkingDataList[replayIndex].r0.toFloat(),
                    state.value.walkingDataList[replayIndex].r1.toFloat(),
                    state.value.walkingDataList[replayIndex].r2.toFloat(),
                    state.value.walkingDataList[replayIndex].r3.toFloat(),
                    state.value.walkingDataList[replayIndex].r4.toFloat(),
                    state.value.walkingDataList[replayIndex].r5.toFloat(),
                    state.value.walkingDataList[replayIndex].r6.toFloat(),
                    state.value.walkingDataList[replayIndex].r7.toFloat(),
                    state.value.walkingDataList[replayIndex].r8.toFloat(),
                    state.value.walkingDataList[replayIndex].r9.toFloat(),
                    state.value.walkingDataList[replayIndex].r10.toFloat(),
                    state.value.walkingDataList[replayIndex].r11.toFloat(),
                    state.value.walkingDataList[replayIndex].r12.toFloat(),
                    state.value.walkingDataList[replayIndex].r13.toFloat(),
                    state.value.walkingDataList[replayIndex].r14.toFloat(),
                    state.value.walkingDataList[replayIndex].r15.toFloat()
                )

                var centerTmp = calculateWeightedCenter(sensorL.value, sensorR.value)
                var centerPointsTmp = currentRecord.value.centerPoints

                centerPointsTmp = if (centerTmp != null) {
                    if (!centerTmp.x.isNaN() && !centerTmp.y.isNaN()) {
                        centerPointsTmp.plus(centerTmp)
                    } else {
                        centerPointsTmp
                    }
                } else {
                    centerPointsTmp
                }
                viewModelScope.launch {
                    _currentRecord.value = currentRecord.value.copy(
                        sensorL = sensorL.value,
                        sensorR = sensorR.value,
                        centerPoints = centerPointsTmp
                    )
                }
                if (replayIndex >= state.value.walkingDataList.size -1) {
                    stopTimerReplay()
                    deciSecondCount = 0
                    replayIndex = 0
                    viewModelScope.launch {
                        _saveButtonEnabled.value = false
                        _startButtonEnabled.value = true
                    }
                }
            }
        }

        // Start the timer after a delay of 1 second and repeat every 2 seconds
        timer.schedule(timerTask, 0, 100)
    }

    private fun stopTimerReplay() {
        if (::timer.isInitialized) {
            timer.cancel()
            viewModelScope.launch {
                _message.value = message_rp1
            }
        }
    }
    // endregion

    // region Save Data
    private fun saveRecordList() {
        recordsToWalkingDataList()
        if (state.value.reportId != -1 && state.value.walkingDataList.isNotEmpty()) {
            viewModelScope.launch {
                walkingUseCases.addWalkingDataList(
                    state.value.walkingDataList
                )
                val dateTime =
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:MM HH:mm"))
                reportUseCases.updateReport(
                    Report(
                        id = state.value.reportId,
                        date_time = dateTime,
                        report_type = state.value.reportType,
                        student_id = state.value.studentId,
                    )
                )
                _message.value = message_rp1
            }
            _saveButtonEnabled.value = false
            _startButtonEnabled.value = true
            _screenMode.value = 1
            _saveButtonText.value = "Pause"
            _startButtonText.value = "Play"
            _message.value = message_rp1
        }
    }
    // endregion

    // region Timer Controls
    private fun decrementTimer() {
        if (timerCount.value > 0) {
            viewModelScope.launch {
                _timerCount.value = timerCount.value - 1
            }
        }
    }

    private fun incrementTimer() {
        if (timerCount.value < maxTimerCount.value) {
            viewModelScope.launch {
                _timerCount.value = timerCount.value + 1
            }
        }
    }
    // endregion

    // region Calculations
    private fun calculateWeightedCenter(valuesL: Array<Float>, valuesR: Array<Float>): PointF? {
//        require(points.size == weights.size) { "Points and weights arrays must have the same size" }

        var totalX = 0f
        var totalY = 0f
        var totalWeight = 0f

        for (i in PointsL.indices) {
            val point = PointsL[i]
            val weight = valuesL[i]

            totalX += point.x * weight
            totalY += point.y * weight
            totalWeight += weight
        }

        for (i in PointsR.indices) {
            val point = PointsR[i]
            val weight = valuesR[i]

            totalX += point.x * weight
            totalY += point.y * weight
            totalWeight += weight
        }

        val centerX = totalX / totalWeight
        val centerY = totalY / totalWeight

        return PointF(centerX, centerY)
    }
    // endregion
}