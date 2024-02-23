package com.jinu.homeautomation.bluetooth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevices>>
    val pairedDevices: StateFlow<List<BluetoothDevices>>
    val errors: SharedFlow<String>

    fun scanDevices()
    fun stopScan()
    fun connectToDevice(device: BluetoothDevices)
    suspend fun trySendMessage(message: String): Boolean

    fun closeConnection()
    fun release()


}