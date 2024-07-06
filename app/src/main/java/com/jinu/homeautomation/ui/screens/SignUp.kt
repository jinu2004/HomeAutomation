package com.jinu.homeautomation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
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

class SignUp(private val navController: NavController) {
    @Composable
    fun View(modifier: Modifier = Modifier) {
        var password by remember {
            mutableStateOf("")
        }

        var confirmPassword by remember {
            mutableStateOf("")
        }

        var passwordIsVisible by remember {
            mutableStateOf(true)
        }
        var confirmPasswordIsVisible by remember {
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
                        Toast.makeText(context, "You,re not authorized", Toast.LENGTH_SHORT).show()
                    }

                    is AuthResult.UnknownError -> {
                        Toast.makeText(
                            context,
                            "An unknown error occurred",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }

        }


        val state = auth.state


        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Sign Up",
                    modifier = Modifier.padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(1000),
                    fontSize = TextUnit(35f, TextUnitType.Sp),
                    fontFamily = FontFamily(Font(R.font.roboto_medium_normal)),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                Image(
                    painter = painterResource(id = R.drawable.sign_up_pana),
                    contentDescription = "",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(top = 1.dp)
                )
            }

            item {
                OutlinedTextField(
                    value = state.username,
                    onValueChange = { auth.onEvent(AuthUiEvent.SignUpUsernameChanged(it)) },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    placeholder = { Text(text = "Username") },
                    label = { Text(text = "Username") },
                    leadingIcon = { Icon(Icons.Outlined.AccountCircle, contentDescription = "") },
                    keyboardActions = KeyboardActions(onNext = {}),
                    keyboardOptions = KeyboardOptions(
                        KeyboardCapitalization.None,
                        imeAction = ImeAction.Next
                    )
                )
            }

            item {
                OutlinedTextField(
                    value = state.password,
                    onValueChange = {
                        password = it; auth.onEvent(
                        AuthUiEvent.SignUpPasswordChanged(
                            it
                        )
                    )
                    },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    placeholder = { Text(text = "Password") },
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
            }

            item {
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    placeholder = { Text(text = "Confirm Password") },
                    label = { Text(text = "Confirm password") },
                    leadingIcon = { Icon(Icons.Outlined.Password, contentDescription = "") },
                    visualTransformation = if (confirmPasswordIsVisible) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordIsVisible = !confirmPasswordIsVisible
                        }) {
                            if (confirmPasswordIsVisible) Icon(Icons.Outlined.VisibilityOff, "")
                            else Icon(Icons.Outlined.Visibility, "")
                        }
                    }

                )
            }

            item {
                Button(onClick = {
                    if (confirmPassword == password) {
                        auth.onEvent(AuthUiEvent.SignUp)
                    } else {
                        Toast.makeText(context, "password must be same", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Sign Up", modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                }
            }

        }


    }
}