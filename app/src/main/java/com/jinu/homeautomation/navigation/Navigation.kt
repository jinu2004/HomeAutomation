package com.jinu.homeautomation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigate(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "") {
        composable(route = "") {

        }
    }
}

sealed class Screens(private val route: String) {
    object HomeScreen : Screens("home_screen")


    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}