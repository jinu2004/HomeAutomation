package com.jinu.homeautomation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jinu.homeautomation.R
import com.jinu.homeautomation.ktorclient.auth.domain.AuthResult
import com.jinu.homeautomation.ktorclient.auth.domain.AuthUiEvent
import com.jinu.homeautomation.ktorclient.auth.domain.AuthViewModel
import com.jinu.homeautomation.ui.navigation.Screens
import org.koin.androidx.compose.getViewModel

class SignInScreen(private val navController: NavController) {
    @Composable
    fun View() {

        var passwordIsVisible by remember {
            mutableStateOf(true)
        }

        val auth = getViewModel<AuthViewModel>()
        val context = LocalContext.current


        LaunchedEffect(auth, context) {
            auth.authResult.collect { result ->
                when (result) {
                    is AuthResult.Authorized -> {
                        navController.navigate(Screens.HomeScreen.route)
                    }

                    is AuthResult.Unauthorized -> {
                        Toast.makeText(
                            context,
                            "username or password is incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
//                        navController.navigate(Screens.SignIn.route)
                    }

                    is AuthResult.UnknownError -> {
                        Toast.makeText(
                            context,
                            "An unknown error occurred",
                            Toast.LENGTH_LONG
                        ).show()
//                        navController.navigate(Screens.SignIn.route)
                    }
                }

            }

        }

        val state = auth.state



        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Sign In",
                modifier = Modifier.padding(top = 30.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(1000),
                fontSize = TextUnit(35f, TextUnitType.Sp),
                fontFamily = FontFamily(Font(R.font.roboto_medium_normal)),
                color = MaterialTheme.colorScheme.primary
            )

            Image(
                painter = painterResource(id = R.drawable.sign_in_pana), contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .padding(top = 10.dp)
            )

            OutlinedTextField(
                value = state.username,
                onValueChange = { auth.onEvent(AuthUiEvent.SignInUsernameChanged(it)) },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(0.8f),
                placeholder = { Text(text = "Username") },
                label = { Text(text = "Username") },
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = "")},
                keyboardActions = KeyboardActions(onNext = {}),
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.None, imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = { auth.onEvent(AuthUiEvent.SignInPasswordChanged(it)) },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(0.8f),
                placeholder = { Text(text = "Password") },
                keyboardActions = KeyboardActions(onDone = { auth.onEvent(AuthUiEvent.SignIn) }),
                label = { Text(text = "password") },
                leadingIcon = { Icon(Icons.Outlined.Password, contentDescription = "") },
                visualTransformation = if (passwordIsVisible) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = {
                        passwordIsVisible = !passwordIsVisible
                    }) {
                        if (passwordIsVisible) Icon(Icons.Outlined.VisibilityOff, "")
                        else Icon(Icons.Outlined.Visibility, "")
                    }
                }
            )

            Text(
                text = "Forgotten password?",modifier = Modifier.padding(top = 10.dp)
            )

            Button(
                onClick = {
                    if (state.password.isNotEmpty() && state.username.isNotEmpty()) {
                        auth.onEvent(AuthUiEvent.SignIn)
                    } else Toast.makeText(context, "Fill all the field", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(30.dp)
            ) {
                Text(text = "Sign in", modifier = Modifier.padding(start = 20.dp, end = 20.dp))
            }


        }
    }
}