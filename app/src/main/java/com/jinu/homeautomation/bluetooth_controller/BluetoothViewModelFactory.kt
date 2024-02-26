package com.jinu.homeautomation.bluetooth_controller

import android.bluetooth.BluetoothAdapter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class BluetoothViewModelFactory(private val adapter: BluetoothAdapter) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BluetoothControllerViewModel(adapter) as T
    }
}