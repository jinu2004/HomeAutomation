package com.jinu.homeautomation.bluetooth

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BluetoothViewModel(private val context: Context):ViewModel(){

    var pairedDevices = MutableLiveData<List<BluetoothDevices>>()

    private val bluetoothControl = BluetoothControl(context)


    init {
        pairedDevices.value = bluetoothControl.pairedDevices.value
    }



    fun scanDevice(){
        bluetoothControl.scanDevices()
    }

    fun getAllPairedDevice(){
        pairedDevices.value = bluetoothControl.pairedDevices.value
    }


    fun stopScan(){
        bluetoothControl.stopScan()
    }

    fun connectToDevice(device: BluetoothDevices){
        bluetoothControl.connectToDevice(device)
    }

    fun trySendMessage(message: String){
         viewModelScope.launch {
            if(bluetoothControl.trySendMessage(message)){
                Toast.makeText(context, "Successful...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun closeConnection(){
        bluetoothControl.closeConnection()
    }


    fun release(){
        bluetoothControl.release()
    }



}