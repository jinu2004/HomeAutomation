package com.jinu.homeautomation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.jinu.homeautomation.bluetooth.BluetoothControl

class BluetoothList(navController: NavController, private val bluetoothControl: BluetoothControl) {

    @SuppressLint("MissingPermission", "StateFlowValueCalledInComposition")
    @Composable
    fun View(modifier: Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }



}