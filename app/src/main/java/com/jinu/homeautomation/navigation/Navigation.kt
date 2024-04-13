package com.jinu.homeautomation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jinu.homeautomation.bluetooth_controller.BluetoothControllerViewModel
import com.jinu.homeautomation.screens.BluetoothList
import com.jinu.homeautomation.screens.DeviceListScreen
import com.jinu.homeautomation.screens.DeviceProvisionScreen
import com.jinu.homeautomation.screens.HomeScreen
import com.jinu.homeautomation.screens.SignInScreen
import com.jinu.homeautomation.screens.SignUp

@Composable
fun Navigate(
    navController: NavHostController,
    bluetoothViewModel: BluetoothControllerViewModel,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = Screens.LogIn.route) {
        composable(route = Screens.HomeScreen.route) {
            HomeScreen(navController).View(modifier)
        }
        composable(route = Screens.DeviceListScreen.route) {
            DeviceListScreen(navController).View(modifier)
        }
        composable(route = Screens.BlueToothList.route) {
            BluetoothList(navController, bluetoothViewModel).View(modifier)
        }
        composable(route = Screens.DeviceProvision.route) {
            DeviceProvisionScreen(navController, bluetoothViewModel).View(modifier)
        }
        composable(route = Screens.SignIn.route){
            SignInScreen().View()
        }
        composable(route = Screens.LogIn.route){
            SignUp().View()
        }
    }
}
//+ "/{room}/{mac}"

sealed class Screens(val route: String) {
    object HomeScreen : Screens("home_screen")
    object BlueToothList : Screens("BlueTooth_list")
    object DeviceListScreen : Screens("Device_List_Screen")
    object DeviceProvision : Screens("Device_Provision")

    object SignIn : Screens("Sign_in")
    object LogIn : Screens("Log_in")

    object BulbController : Screens("bulb_controller")
    object FanController : Screens("Fan_Controller")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}