package com.jinu.homeautomation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jinu.homeautomation.bluetooth_controller.BluetoothControllerViewModel
import com.jinu.homeautomation.ui.screens.BluetoothList
import com.jinu.homeautomation.ui.screens.BulbController
import com.jinu.homeautomation.ui.screens.DeviceListScreen
import com.jinu.homeautomation.ui.screens.DeviceProvisionScreen
import com.jinu.homeautomation.ui.screens.FanController
import com.jinu.homeautomation.ui.screens.HomeScreen
import com.jinu.homeautomation.ui.screens.SignInScreen
import com.jinu.homeautomation.ui.screens.SignUp

@Composable
fun Navigate(
    navController: NavHostController,
    bluetoothViewModel: BluetoothControllerViewModel,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = Screens.FanController.route) {
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
        composable(route= Screens.BulbController.route){
            BulbController().View()
        }
        composable(route = Screens.FanController.route){
            FanController().View()
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