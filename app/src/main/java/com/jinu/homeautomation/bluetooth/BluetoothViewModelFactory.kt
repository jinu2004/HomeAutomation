package com.jinu.homeautomation.bluetooth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class BluetoothViewModelFactory(private val context: Context):
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BluetoothViewModel(context) as T
    }
}