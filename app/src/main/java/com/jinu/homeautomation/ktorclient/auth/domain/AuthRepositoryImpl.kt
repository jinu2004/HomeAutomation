package com.jinu.homeautomation.ktorclient.auth.domain

import android.content.SharedPreferences
import com.jinu.homeautomation.ktorclient.auth.data.Res
import com.jinu.homeautomation.ktorclient.auth.data.TokenResponse
import com.jinu.homeautomation.ktorclient.auth.data.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType

class AuthRepositoryImpl(
    private val client: HttpClient,
    private val preference: SharedPreferences
) :
    AuthRepository {

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {

        return try {
            val response = client.post {
                url(Res.AuthUrl.SIGN_IN)
                contentType(ContentType.Application.Json)
                setBody(User(username, password))
            }
            val token = response.body<TokenResponse>()
            preference.edit().putString("jwt",token.token).apply()
            AuthResult.Authorized()

        }
        catch (e: RedirectResponseException) {
            if (e.response.status.value == 401) {
                e.printStackTrace()
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResult.UnknownError()
        }

    }

    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            client.post {
                url(Res.AuthUrl.SIGN_UP)
                contentType(ContentType.Application.Json)
                setBody(User(username, password))

            }
            signIn(username,password)


            AuthResult.Authorized()
        }
        catch (e: RedirectResponseException) {
            if (e.response.status.value == 401) {
                e.printStackTrace()
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            AuthResult.UnknownError()


        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = preference.getString("jwt", null) ?: return AuthResult.Unauthorized()
            client.get {
                url(Res.AuthUrl.AUTHENTICATE)
                headers { append(HttpHeaders.Authorization, "Bearer $token") }
            }
            AuthResult.Authorized()

        } catch (e: RedirectResponseException) {
            if (e.response.status.value == 401) {
                AuthResult.Unauthorized()
            } else {
                AuthResult.UnknownError()
            }
        } catch (e: Exception) {
            AuthResult.UnknownError()
        }
    }

    override suspend fun getUserId(): String {
        return ""
    }

}