package com.jinu.homeautomation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jinu.homeautomation.bluetooth.BluetoothControl
import com.jinu.homeautomation.navigation.Navigate
import com.jinu.homeautomation.navigation.Screens
import com.jinu.homeautomation.ui.theme.HomeAutomationTheme

class MainActivity : ComponentActivity() {
    private val REQUEST_ENABLE_BLUETOOTH = 0
    private lateinit var currentBluetoothAdapter: BluetoothAdapter
    private lateinit var navController: NavHostController

    @SuppressLint("StateFlowValueCalledInComposition", "MissingPermission")
    @RequiresApi(Build.VERSION_CODES.S)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val permission = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_ADMIN
                )
            )
            val blutoothController = BluetoothControl(LocalContext.current)
            val currentBluetoothAdapter = getSystemService(BluetoothManager::class.java).adapter










            HomeAutomationTheme {
                navController = rememberNavController()
                val lifecycleOwner = LocalLifecycleOwner.current
                DisposableEffect(key1 = lifecycleOwner, effect = {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_START) {
                            permission.launchMultiplePermissionRequest()
                        }
                    }
                    lifecycleOwner.lifecycle.addObserver(observer)

                    onDispose {
                        lifecycleOwner.lifecycle.removeObserver(observer)
                    }
                })
                permission.permissions.forEach {
                    when (it.permission) {
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE -> {
                            when {
                                it.hasPermission -> {
                                    Text(text = "Camera permission accepted")
                                }

                                it.shouldShowRationale -> {
                                    Text(
                                        text = "Camera permission is needed" + "to access the camera"
                                    )
                                }
                            }
                        }

                        else -> {}
                    }
                }



                Scaffold(topBar = {}, floatingActionButton = {
                    FloatingActionButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "",
                            modifier = Modifier.padding(20.dp)
                        )
                    }
                }, floatingActionButtonPosition = FabPosition.Center
                ) {
                    Navigate(
                        navController = navController,
                        bluetoothControl = blutoothController,
                        modifier = Modifier.padding(it.calculateTopPadding())
                    )
                    if (!currentBluetoothAdapter.isEnabled && navController.currentDestination?.route == Screens.BlueToothList.route) {
                        val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        @Suppress("DEPRECATION") startActivityForResult(
                            enableBluetoothIntent,
                            REQUEST_ENABLE_BLUETOOTH
                        )
                    }

                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        @Suppress("DEPRECATION") super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (currentBluetoothAdapter.isEnabled) {
                    Toast.makeText(baseContext, "Bluetooth has been enabled", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(baseContext, "Bluetooth has been disabled", Toast.LENGTH_SHORT)
                        .show()
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(
                    baseContext, "Bluetooth enabling has been canceled", Toast.LENGTH_SHORT
                ).show()
            }
        }


    }
}
