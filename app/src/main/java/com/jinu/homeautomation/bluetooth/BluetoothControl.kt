package com.jinu.homeautomation.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.registerReceiver
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.jinu.homeautomation.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID
import kotlin.coroutines.CoroutineContext

@SuppressLint("MissingPermission")
class BluetoothControl(private val context: Context) : BluetoothController {
    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private var currentServerSocket: BluetoothServerSocket? = null
    private var currentClientSocket: BluetoothSocket? = null


    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean>
        get() = _isConnected.asStateFlow()

    private val _scannedDevices = MutableStateFlow<List<BluetoothDevices>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDevices>>
        get() = _scannedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothDevices>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDevices>>
        get() = _pairedDevices.asStateFlow()

    private val _errors = MutableSharedFlow<String>()
    override val errors: SharedFlow<String>
        get() = _errors.asSharedFlow()

    private val foundDeviceReceiver = FoundDeviceReceiver { device ->
        _scannedDevices.update {
            it + BluetoothDevices(device.name, device.address)
        }
    }

    private val bluetoothStateReceiver = BluetoothStateReceiver { isConnected, bluetoothDevice ->
        if (bluetoothAdapter?.bondedDevices?.contains(bluetoothDevice) == true) {
            _isConnected.update { isConnected }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                _errors.emit("Can't connect to a non-paired device.")
            }
        }
    }


    init {
        updatePairedDevices()
        context.registerReceiver(
            bluetoothStateReceiver,
            IntentFilter().apply {
                addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
                addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
                addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
            }
        )
    }


    override fun scanDevices() {
        if (hasPermission(Manifest.permission.BLUETOOTH_SCAN) && bluetoothAdapter?.isEnabled == true) {

            context.registerReceiver(
                foundDeviceReceiver,
                IntentFilter(BluetoothDevice.ACTION_FOUND)
            )

            updatePairedDevices()

            bluetoothAdapter?.startDiscovery()
        }
        else{
            Toast.makeText(context, "Bluetooth permission is missing", Toast.LENGTH_SHORT).show()
        }

    }

    override fun stopScan() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
            return
        }

        bluetoothAdapter?.cancelDiscovery()
    }

    override fun connectToDevice(device: BluetoothDevices) {
            if (hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                currentClientSocket = bluetoothAdapter?.getRemoteDevice(device.address)
                    ?.createRfcommSocketToServiceRecord(
                        UUID.fromString(SERVICE_UUID)
                    )
            }
            else{
                Toast.makeText(context, "Bluetooth permission is missing", Toast.LENGTH_SHORT).show()
            }
            bluetoothAdapter?.cancelDiscovery()

            currentClientSocket?.let { bluetoothSocket ->
                try {
                    bluetoothSocket.connect()
                    Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    bluetoothSocket.close()
                    currentClientSocket = null
                }

            }

    }

    override suspend fun trySendMessage(message: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                currentClientSocket?.outputStream?.write(message.toByteArray())
            } catch (e: IOException) {
                e.printStackTrace()
                return@withContext false
            }
            true
        }

    }

    override fun closeConnection() {
        currentClientSocket?.close()
        currentServerSocket?.close()
        currentServerSocket = null
        currentClientSocket = null
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
        context.unregisterReceiver(bluetoothStateReceiver)
        closeConnection()
    }


    inner class FoundDeviceReceiver(
        private val onDeviceFound: (BluetoothDevice) -> Unit
    ) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                BluetoothDevice.ACTION_FOUND -> {
                    val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent.getParcelableExtra(
                            BluetoothDevice.EXTRA_DEVICE,
                            android.bluetooth.BluetoothDevice::class.java
                        )
                    } else {
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    }

                    device?.let(onDeviceFound)
                }
            }
        }

    }

    inner class BluetoothStateReceiver(
        private val onStateChanged: (isConnected: Boolean, BluetoothDevice) -> Unit
    ) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val device = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra(
                    BluetoothDevice.EXTRA_DEVICE,
                    BluetoothDevice::class.java
                )
            } else {
                intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
            }
            when (intent?.action) {
                BluetoothDevice.ACTION_ACL_CONNECTED -> {
                    onStateChanged(true, device ?: return)
                }

                BluetoothDevice.ACTION_ACL_DISCONNECTED -> {
                    onStateChanged(false, device ?: return)
                }
            }
        }
    }


    private fun updatePairedDevices() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
            return
        }
        bluetoothAdapter
            ?.bondedDevices
            ?.map { BluetoothDevices(it.name, it.address) }
            ?.also { devices ->
                _pairedDevices.update { devices }
            }
    }

    fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val SERVICE_UUID = "27b7d1da-08c7-4505-a6d1-2459987e5e2d"
    }


}