package com.jinu.homeautomation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jinu.homeautomation.R
import com.jinu.homeautomation.bluetooth.BluetoothViewModel

class BluetoothList(
    navController: NavController,
    private val bluetoothControl: BluetoothViewModel
) {


    @SuppressLint("MissingPermission", "StateFlowValueCalledInComposition")
    @Composable
    fun View(modifier: Modifier) {


        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.smart_home_bro),
                contentDescription = "",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Paired Devices",
                fontSize = TextUnit(
                    20f,
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
                color = Color.Gray,
                modifier = Modifier.padding(start = 30.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                bluetoothControl.pairedDevices.observeForever {
                    items(it) {
                        it.name?.let { it1 ->
                            OutlinedButton(
                                onClick = { /*TODO*/ }, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 30.dp, end = 30.dp)
                            ) {
                                Text(
                                    text = it1,
                                    fontSize = TextUnit(
                                        16f,
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
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }

                item {
                    Button(onClick = {bluetoothControl.getAllPairedDevice()}) {
                        Text(text = "Refresh")

                    }
                }
            }

        }
    }


}