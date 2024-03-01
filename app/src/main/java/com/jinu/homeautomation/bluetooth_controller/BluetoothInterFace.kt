package com.jinu.homeautomation.bluetooth_controller

import androidx.lifecycle.LiveData

interface BluetoothInterFace {
    fun getPairedDevices(): LiveData<List<BluetoothDevices>>

    suspend fun connectRemoteDevice(devices: BluetoothDevices): Boolean


    fun sendMessage(message: String)

    fun disconnect()


}