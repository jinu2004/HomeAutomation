package com.jinu.homeautomation.ktorclient.auth.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jinu.homeautomation.ktorclient.auth.domain.AuthRepository
import com.jinu.homeautomation.ktorclient.auth.domain.AuthResult
import com.jinu.homeautomation.ktorclient.auth.domain.AuthUiEvent
import com.jinu.homeautomation.ui.state.AuthState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    var state by mutableStateOf(AuthState())
    private val resultChannel = Channel<AuthResult<Unit>>()
    val authResult = resultChannel.receiveAsFlow()


    init {
        authenticate()
    }

    fun onEvent(event: AuthUiEvent) {
        when (event) {
            is AuthUiEvent.SignInUsernameChanged -> {
                state = state.copy(username = event.value)
            }

            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(password = event.value)
            }

            is AuthUiEvent.SignIn -> {
                signIn()
            }

            is AuthUiEvent.SignUpUsernameChanged -> {
                state = state.copy(username = event.value)
            }

            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(password = event.value)
            }

            is AuthUiEvent.SignUp -> {
                signUp()
            }
        }
    }


    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signUp(state.username, state.password)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }


    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.signIn(state.username, state.password)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = repository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}

