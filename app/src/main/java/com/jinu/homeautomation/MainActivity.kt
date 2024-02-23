package com.jinu.homeautomation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.jinu.homeautomation.screens.DeviceListScreen
import com.jinu.homeautomation.screens.HomeScreen
import com.jinu.homeautomation.ui.theme.HomeAutomationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeAutomationTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {},
                    floatingActionButton = {
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "",
                                modifier = Modifier.padding(20.dp)
                            )
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center
                ) {
                    DeviceListScreen(navController).View(modifier = Modifier.padding(it))
                }
            }
        }
    }
}
