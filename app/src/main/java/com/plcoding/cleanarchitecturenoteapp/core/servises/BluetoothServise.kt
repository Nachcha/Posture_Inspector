package com.plcoding.cleanarchitecturenoteapp.core.servises

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class BluetoothService(private val listener: Listener, private val context: Context) {

    interface Listener {
        fun onMessageReceived(device: String, message: String)
        fun onError(errorMessage: String)
    }

    private val bluetoothManager: BluetoothManager =
        context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    private val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter
    private lateinit var leftDevice: BluetoothDevice
    private lateinit var rightDevice: BluetoothDevice
    private lateinit var readThreadLeft: Thread
    private lateinit var readThreadRight: Thread

    private var leftDeviceSocket: BluetoothSocket? = null
    private var rightDeviceSocket: BluetoothSocket? = null
    private var leftDeviceInputStream: InputStream? = null
    private var rightDeviceInputStream: InputStream? = null
    private var leftDeviceOutputStream: OutputStream? = null
    private var rightDeviceOutputStream: OutputStream? = null

    fun connectToDevice() {
        checkForDevices()
        val leftDevice = bluetoothAdapter.getRemoteDevice(leftDevice.address)
        val rightDevice = bluetoothAdapter.getRemoteDevice(rightDevice.address)
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw Error("Permissions are denied to connect deviceL")
            }
            leftDeviceSocket =
                leftDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))

            try {
                leftDeviceSocket?.connect()
                Log.d("BlueToothL", "Socket connected...")
            } catch (e: IOException) {
                Log.e("BlueToothL", "Socket connection failed..."+e.message)
            }

            leftDeviceInputStream = leftDeviceSocket?.inputStream
            Log.d("BlueToothL", "Input stream." + leftDeviceSocket?.inputStream)

            leftDeviceOutputStream = leftDeviceSocket?.outputStream
            Log.d("BlueToothL", "output stream." + leftDeviceSocket?.inputStream)

            startListening(leftDeviceInputStream, leftDevice.name,"Left")
            Log.d("BlueToothL", "Socket listener mounted.")
        } catch (e: IOException) {
            listener.onError("Failed to connect to deviceL: ${e.message}")
        }

        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw Error("Permissions are denied to connect deviceR")
            }
            rightDeviceSocket =
                rightDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))

            try {
                rightDeviceSocket?.connect()
                Log.d("BlueToothR", "Socket connected...")
            } catch (e: IOException) {
                Log.e("BlueToothR", "Socket connection failed..."+e.message)
            }

            rightDeviceInputStream = rightDeviceSocket?.inputStream
            Log.d("BlueToothR", "Input stream." + rightDeviceSocket?.inputStream)

            rightDeviceOutputStream = rightDeviceSocket?.outputStream
            Log.d("BlueToothR", "output stream." + rightDeviceSocket?.inputStream)

            startListening(rightDeviceInputStream, rightDevice.name,"Right")
            Log.d("BlueToothR", "Socket listener mounted.")
        } catch (e: IOException) {
            listener.onError("Failed to connect to deviceR: ${e.message}")
        }
    }

    private fun startListening(inputStream: InputStream?, deviceName: String, sensorSide: String) {
        when (sensorSide) {
            "Left" -> {
                readThreadLeft = Thread {
                    val buffer = ByteArray(1024)
                    var bytes: Int
                    while (true) {
                        try {
                            bytes = inputStream?.read(buffer) ?: 0
                            val message = String(buffer, 0, bytes)
                            listener.onMessageReceived(deviceName, message)
                        } catch (e: IOException) {
                            listener.onError("Failed to read data from $deviceName: ${e.message}")
                            break
                        }
                    }
                }
                readThreadLeft.start()
            }
            "Right" -> {
                readThreadRight = Thread {
                    val buffer = ByteArray(1024)
                    var bytes: Int
                    while (true) {
                        try {
                            bytes = inputStream?.read(buffer) ?: 0
                            val message = String(buffer, 0, bytes)
                            listener.onMessageReceived(deviceName, message)
                        } catch (e: IOException) {
                            listener.onError("Failed to read data from $deviceName: ${e.message}")
                            break
                        }
                    }
                }
                readThreadRight.start()
            }
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
    }

    private fun checkForDevices() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw Error("Permissions are denied to connect left device")
            }
            leftDevice = bluetoothAdapter.bondedDevices.find { bluetoothDevice ->
                bluetoothDevice.name == "Left_foot"
            }!!
        } catch (e: IOException) {
            listener.onError("Failed to connect to deviceL: ${e.message}")
        }

        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                throw Error("Permissions are denied to connect right device")
            }
            rightDevice = bluetoothAdapter.bondedDevices.find { bluetoothDevice ->
                bluetoothDevice.name == "Right_foot"
            }!!
        } catch (e: IOException) {
            listener.onError("Failed to connect to deviceR: ${e.message}")
        }
    }

//    fun sendToFirstDevice(message: String) {
//        firstDeviceOutputStream?.write(message.toByteArray())
//    }

    fun disconnectFromDevices() {
        try {
            leftDeviceSocket?.close()
        } catch (e: IOException) {
            listener.onError("Failed to disconnect from deviceL: ${e.message}")
        }
        try {
            rightDeviceSocket?.close()
        } catch (e: IOException) {
            listener.onError("Failed to disconnect from deviceR: ${e.message}")
        }
    }
}