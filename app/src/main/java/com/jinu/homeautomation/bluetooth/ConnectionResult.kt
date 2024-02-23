package com.jinu.homeautomation.bluetooth

sealed class ConnectionResult {
    object ConnectionEstablished: ConnectionResult()
    data class TransferSucceeded(val message: String): ConnectionResult()
    data class Error(val message: String): ConnectionResult()
}
