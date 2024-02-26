package com.jinu.homeautomation.bluetooth_controller

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.IOException
import java.util.UUID

@SuppressLint("MissingPermission")
class BluetoothController(private val adapter: BluetoothAdapter) :
    BluetoothInterFace {

    private var bluetoothSocket: BluetoothSocket? = null
    private var _pairedDevice = MutableLiveData<List<BluetoothDevices>>()
    private var uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    var isConnected = MutableLiveData(false)

    override fun getPairedDevices(): LiveData<List<BluetoothDevices>> {
        val pairedDevices = adapter.bondedDevices
        pairedDevices
            ?.map { BluetoothDevices(it.name, it.address) }
            ?.also { devices ->
                _pairedDevice.value = devices
            }
        return _pairedDevice
    }

    override suspend fun connectRemoteDevice(devices: BluetoothDevices): Boolean {
        return try {
            if (bluetoothSocket == null || !bluetoothSocket!!.isConnected) {
                val device: BluetoothDevice = adapter.getRemoteDevice(devices.address)
                bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(uuid)
                adapter.cancelDiscovery()
                bluetoothSocket!!.connect()
                isConnected.value = (bluetoothSocket!!.isConnected)
            }
            bluetoothSocket!!.isConnected
        } catch (e: IOException) {
            false
        }
    }

    override fun sendMessage(message: String) {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket!!.outputStream.write(message.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun disconnect() {
        if (bluetoothSocket != null) {
            try {
                bluetoothSocket!!.close()
                bluetoothSocket = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}