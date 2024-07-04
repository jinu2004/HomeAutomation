package com.jinu.homeautomation.ui.state

data class AuthState(
    val isLoading: Boolean = false,
    val username: String = "",
    val password: String = "",
)
