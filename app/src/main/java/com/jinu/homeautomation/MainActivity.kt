@file:Suppress("DEPRECATION")

package com.jinu.homeautomation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jinu.homeautomation.bluetooth_controller.BluetoothControllerViewModel
import com.jinu.homeautomation.bluetooth_controller.BluetoothViewModelFactory
import com.jinu.homeautomation.navigation.Navigate
import com.jinu.homeautomation.navigation.Screens
import com.jinu.homeautomation.ui.theme.HomeAutomationTheme

class MainActivity : ComponentActivity() {
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
            val currentBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

            val bleViewModel: BluetoothControllerViewModel =
                viewModel(factory = BluetoothViewModelFactory(currentBluetoothAdapter))










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




                Scaffold(
                    floatingActionButton = {

                        when (navController.currentDestination?.route) {
                            Screens.HomeScreen.route -> {
                                FloatingActionButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "",
                                        modifier = Modifier.padding(20.dp)
                                    )
                                }
                            }

                            Screens.DeviceListScreen.route -> {
                                FloatingActionButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Add,
                                        contentDescription = "",
                                        modifier = Modifier.padding(20.dp)
                                    )
                                }
                            }

                            else -> {}

                        }


                    }, floatingActionButtonPosition = FabPosition.Center
                ) {
                    Navigate(
                        navController = navController,
                        bluetoothViewModel = bleViewModel,
                        modifier = Modifier.padding(it.calculateTopPadding())
                    )
                    if (!currentBluetoothAdapter.isEnabled && navController.currentDestination?.route == Screens.BlueToothList.route) {
                        val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        @Suppress("DEPRECATION") startActivityForResult(
                            enableBluetoothIntent,
                            0
                        )
                    }

                }
            }
        }
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        currentBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        @Suppress("DEPRECATION") super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
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
