package com.jinu.homeautomation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.jinu.homeautomation.R

class SignUp {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun View() {
        var email by remember {
            mutableStateOf("")
        }
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


        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top),
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
                    value = email,
                    onValueChange = { email = it },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    placeholder = { Text(text = "Email") },
                    label = { Text(text = "Email") },
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = "") },
                    keyboardActions = KeyboardActions(onNext = {}),
                    keyboardOptions = KeyboardOptions(
                        KeyboardCapitalization.None,
                        imeAction = ImeAction.Next
                    )
                )
            }

            item {
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
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
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Sign Up", modifier = Modifier.padding(start = 10.dp, end = 10.dp))
                }
            }

        }


    }
}