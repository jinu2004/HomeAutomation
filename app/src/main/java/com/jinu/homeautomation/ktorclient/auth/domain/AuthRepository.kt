package com.jinu.homeautomation.ktorclient.auth.domain

import com.jinu.homeautomation.ktorclient.auth.domain.AuthResult

interface AuthRepository {
    suspend fun signIn(username: String, password: String): AuthResult<Unit>
    suspend fun signUp(username: String,password: String): AuthResult<Unit>

    suspend fun authenticate(): AuthResult<Unit>

    suspend fun getUserId():String


}