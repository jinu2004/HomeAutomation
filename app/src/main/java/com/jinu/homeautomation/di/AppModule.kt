package com.jinu.homeautomation.di

import android.bluetooth.BluetoothAdapter
import android.content.Context
import com.jinu.homeautomation.bluetooth_controller.BluetoothControllerViewModel
import com.jinu.homeautomation.ktorclient.auth.domain.AuthRepository
import com.jinu.homeautomation.ktorclient.auth.domain.AuthRepositoryImpl
import com.jinu.homeautomation.ktorclient.auth.domain.AuthViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation){
                json()
            }

        }
    }
    single {
        androidApplication().getSharedPreferences("HomeAutomation", Context.MODE_PRIVATE)
    }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }

    single { BluetoothAdapter.getDefaultAdapter() }

    viewModel { AuthViewModel(get()) }

    viewModel { BluetoothControllerViewModel(get()) }

}
