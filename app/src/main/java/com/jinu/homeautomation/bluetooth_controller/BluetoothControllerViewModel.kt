package com.jinu.homeautomation.bluetooth_controller

import android.bluetooth.BluetoothAdapter
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BluetoothControllerViewModel(adapter: BluetoothAdapter) : ViewModel() {

    private var bluetoothController = BluetoothController(adapter)
    private var isSucess: Boolean = false
    fun getPairedDevices(): LiveData<List<BluetoothDevices>> {
        return bluetoothController.getPairedDevices()
    }

    fun connectRemoteDevice(devices: BluetoothDevices): Boolean {
        viewModelScope.launch(Dispatchers.IO) {
            val async = async { return@async bluetoothController.connectRemoteDevice(devices) }
            isSucess = async.await()
        }
        return isSucess

    }

    fun sendMessage(message: String) {
        bluetoothController.sendMessage(message)
    }

    fun disconnect() {
        bluetoothController.disconnect()
    }

}