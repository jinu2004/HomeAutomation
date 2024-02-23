package com.jinu.homeautomation.bluetooth

data class BluetoothUiState(
    val scannedDevices: List<BluetoothDevices> = emptyList(),
    val pairedDevices: List<BluetoothDevices> = emptyList(),
    val isConnected: Boolean = false,
    val isConnecting: Boolean = false,
    val errorMessage: String? = null,
)