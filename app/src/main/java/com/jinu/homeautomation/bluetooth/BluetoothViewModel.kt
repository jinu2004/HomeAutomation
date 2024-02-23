package com.jinu.homeautomation.bluetooth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BluetoothViewModel(private val bluetoothController: BluetoothController) : ViewModel() {

    private val _state = MutableStateFlow(BluetoothUiState())
    private var deviceConnectionJob: Job? = null
    val state = combine(
        bluetoothController.scannedDevices,
        bluetoothController.pairedDevices,
        _state
    ) { scannedDevices, pairedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices,
            pairedDevices = pairedDevices,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)


    init {
        bluetoothController.isConnected.onEach { connect -> _state.update { it.copy(isConnected = connect) } }
            .launchIn(viewModelScope)
        bluetoothController.errors.onEach { error -> _state.update { it.copy(errorMessage = error) } }
            .launchIn(viewModelScope)
    }

    fun connectToDevice(devices: BluetoothDevices) {
        _state.update { it.copy(isConnected = true) }
        deviceConnectionJob.run { bluetoothController.connectToDevice(devices) }
    }

    fun disconnectFromJob() {
        deviceConnectionJob?.cancel()
        bluetoothController.closeConnection()
        _state.update { it.copy(isConnecting = false) }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            bluetoothController.trySendMessage(message)
        }
    }

    fun stopScan() {
        bluetoothController.stopScan()
    }

    fun startScan() {
        bluetoothController.scanDevices()
    }


}