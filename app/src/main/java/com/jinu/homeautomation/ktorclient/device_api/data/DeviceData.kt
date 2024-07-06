package com.jinu.homeautomation.ktorclient.device_api.data

import kotlinx.serialization.Serializable

@Serializable
data class DeviceData(
    val deviceName: String,
    val deviceId:String,
    val userId:String,
    val type: String,
    val status: Boolean,
    val color: String,
    val speed: Float,
    val var1: String,
    var var2: String
)
