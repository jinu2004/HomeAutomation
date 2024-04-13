package com.jinu.homeautomation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkWifi
import androidx.compose.material.icons.filled.WifiLock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jinu.homeautomation.R
import com.jinu.homeautomation.bluetooth_controller.BluetoothControllerViewModel

class DeviceProvisionScreen(
    private val navController: NavController,
    private val bluetoothControl: BluetoothControllerViewModel
) {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun View(modifier: Modifier) {

        var ssd by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var roomName by remember {
            mutableStateOf("")
        }


        Column(
            modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Connect To Device",
                fontSize = TextUnit(
                    25f,
                    TextUnitType.Sp
                ),
                fontWeight = FontWeight(1000),
                fontFamily = FontFamily(
                    Font(
                        resId = R.font.roboto_medium,
                        FontWeight.Medium,
                        style = FontStyle.Normal
                    )
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.smart_home_amico),
                contentDescription = "",
                modifier = Modifier.size(250.dp)
            )

            OutlinedTextField(
                value = ssd,
                onValueChange = { string -> ssd = string },
                label = { Text(text = "Enter Wifi-network name") },
                placeholder = {
                    Text(
                        text = ssd, letterSpacing = TextUnit(
                            10f,
                            TextUnitType.Sp
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 30.dp, end = 30.dp),
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.NetworkWifi,
                            contentDescription = null
                        )
                    }
                },
                singleLine = true,
            )

            OutlinedTextField(
                value = password,
                onValueChange = { string -> password = string },
                label = { Text(text = "Enter Wifi-network password") },
                placeholder = {
                    Text(
                        text = ssd, letterSpacing = TextUnit(
                            10f,
                            TextUnitType.Sp
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 30.dp, end = 30.dp),
                trailingIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.WifiLock,
                            contentDescription = null
                        )
                    }
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )
            Button(onClick = { bluetoothControl.sendMessage("hay") }) {
                Text(text = "message")
            }


        }
    }
}