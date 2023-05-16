package com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cleanarchitecturenoteapp.core.servises.BluetoothService
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.InvalidStandStillDataException
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.Report
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.model.StandStillData
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case.ReportUseCases
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.use_case.StandStillUseCases
import com.plcoding.cleanarchitecturenoteapp.featue_student.domain.util.StandStillDataDTO
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.utils.HeatMapDataObject
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.utils.message_rd1
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.utils.message_rd2
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.stand_still_test.utils.message_rd3
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.PointsL
import com.plcoding.cleanarchitecturenoteapp.featue_student.presentation.util.PointsR
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class StandStillTestViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak") @ApplicationContext val context: Context,
    private val standStillUseCases: StandStillUseCases,
    private val reportUseCases: ReportUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(StandStillTestState())
    val state: State<StandStillTestState> = _state

    private var getStandStillDataListJob: Job? = null
    private var standStillDataRow: StandStillData? = null

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
        mutableStateOf(HeatMapDataObject(sensorL.value, sensorR.value, emptyList()))
    val currentRecord: State<HeatMapDataObject> = _currentRecord

//    private var records: Array<StandStillDataDTO> = emptyArray()

    private var records = mutableListOf<StandStillDataDTO>()

    private val _message = mutableStateOf("")
    val message: State<String> = _message

    private val _startButtonEnabled = mutableStateOf(true)
    val startButtonEnabled: State<Boolean> = _startButtonEnabled

    private val _saveButtonEnabled = mutableStateOf(false)
    val saveButtonEnabled: State<Boolean> = _saveButtonEnabled

//    private val _reportId = mutableStateOf(-1)
//    val reportId: State<Int> = _reportId

    private lateinit var timer: Timer
    private var deciSecondCount: Int = 0

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

    init {
        savedStateHandle.get<Int>("reportId")?.let { reportId ->
            Log.d("ReportData", "reportId: $reportId")
            if (reportId != -1) {
                _state.value = state.value.copy(
                    reportId = reportId,
                )
                getStandStillDataList(state.value.reportId)
            }
        }

        savedStateHandle.get<String>("dateTime")?.let { dateTime ->
            Log.d("ReportData", "reportId: $dateTime")
            if (dateTime != "") {
                _state.value = state.value.copy(
                    dateTime = dateTime,
                )
            }
        }

        savedStateHandle.get<Int>("studentId")?.let { studentId ->
            Log.d("ReportData", "reportId: $studentId")
            if (studentId != -1) {
                _state.value = state.value.copy(
                    studentId = studentId,
                )
            }
        }

        savedStateHandle.get<String>("reportType")?.let { reportType ->
            Log.d("ReportData", "reportId: $reportType")
            if (reportType != "") {
                _state.value = state.value.copy(
                    reportType = reportType,
                )
            }
        }

        viewModelScope.launch() {
            _message.value = message_rd1
        }
    }

    fun onEvent(event: StandStillTestEvent) {
        when (event) {
            is StandStillTestEvent.FetchData -> {
//                getStandStillDataList(state.value.reportId)
            }
            is StandStillTestEvent.InsertStandStillDataRow -> {
                viewModelScope.launch {
                    standStillUseCases.addStandStillData(standStillDataRow ?: return@launch)
                }
            }
            is StandStillTestEvent.GenerateHeatMap -> {

            }
            is StandStillTestEvent.OnStart -> {
                startRecording()
            }
            is StandStillTestEvent.OnSave -> {
                saveRecordList()
            }
        }
    }

    private fun getStandStillDataList(reportId: Int?) {
        if (reportId != null) {
            getStandStillDataListJob?.cancel()
            getStandStillDataListJob = standStillUseCases.getStandStillDataList(reportId)
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
            throw InvalidStandStillDataException("Something went wrong. Student ID is missing.")
        }
    }

    private fun startRecording() {
        bluetoothSerial.connectToDevice()
        viewModelScope.launch {
            _timerCount.value = 20
        }
        startTimer()
    }

    private fun stopRecording() {
        bluetoothSerial.disconnectFromDevices()
        stopTimer()
    }

    private fun saveRecordList() {
        recordsToStandStillDataList()
        if (state.value.reportId != -1 && state.value.standStillDataList.isNotEmpty()) {
            viewModelScope.launch {
                Log.d("saveRecordList", "addStandStillDataList: Started")
                standStillUseCases.addStandStillDataList(
                    state.value.standStillDataList
                )
                Log.d("saveRecordList", "updateReport: Started")
                val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd:MM HH:mm"))
                reportUseCases.updateReport(
                    Report(
                        id = state.value.reportId,
                        date_time = dateTime,
                        report_type = state.value.reportType,
                        student_id = state.value.studentId,
                    )
                )
                Log.d("saveRecordList", "completed")
            }
            _saveButtonEnabled.value = false
        }
    }

    private fun recordsToStandStillDataList() {
        val standDataList = mutableListOf<StandStillData>()
        records.forEach { record ->
            standDataList.add(
                StandStillData(
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
            standStillDataList = standDataList
        )
    }

    private fun startTimer() {
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
                    val newRecord = StandStillDataDTO(
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

    private fun stopTimer() {
        if (::timer.isInitialized) {
            timer.cancel()
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }

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
}