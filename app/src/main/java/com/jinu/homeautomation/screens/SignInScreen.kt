package com.jinu.homeautomation.screens

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

class SignInScreen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun View() {
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }

        var passwordIsVisible by remember {
            mutableStateOf(true)
        }

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
                value = email,
                onValueChange = { email = it },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxWidth(0.8f),
                placeholder = { Text(text = "Email") },
                label = { Text(text = "Email") },
                leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = "")},
                keyboardActions = KeyboardActions(onNext = {}),
                keyboardOptions = KeyboardOptions(KeyboardCapitalization.None, imeAction = ImeAction.Next)
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .padding(top = 20.dp)
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

            Text(
                text = "Forgotten password?",modifier = Modifier.padding(top = 10.dp)
            )

            Button(onClick = { /*TODO*/ }, modifier = Modifier.padding(30.dp)) {
                Text(text = "Sign in", modifier = Modifier.padding(start = 20.dp, end = 20.dp))
            }


        }
    }
}