package com.jinu.homeautomation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.jinu.homeautomation.R
import com.jinu.homeautomation.ui.components.CircularSeekbarView

class FanController {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun View(){
        val navController = rememberNavController()
        var fanSpeed by remember {
            mutableFloatStateOf(0f)
        }


        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Fan",
                        modifier = Modifier.padding(20.dp),
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontSize = TextUnit(20f, TextUnitType.Sp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.ArrowBackIosNew, "")
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Outlined.Edit, "")
                    }
                },
            )

            Text(
                text = "Control fan speed", textAlign = TextAlign.Center, fontSize = TextUnit(
                    30f,
                    TextUnitType.Sp
                ), modifier = Modifier.padding(top = 50.dp),
                fontFamily = FontFamily(Font(R.font.roboto_medium_normal))
            )

            Box {
                val color = MaterialTheme.colorScheme.surface
                CircularSeekbarView(
                    value = fanSpeed,
                    onChange = { fanSpeed = it },
                    lineWeight = 30.dp,
                    modifier = Modifier.padding(50.dp),
                    dotRadius = 10.dp,
                    dotColor = MaterialTheme.colorScheme.onPrimary,

                )

                Text(
                    text = "${ (fanSpeed*100).toInt()}%",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = TextUnit(
                        50f,
                        TextUnitType.Sp
                    ),
                    fontFamily = FontFamily(Font(resId = R.font.orbitron_black)),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                    softWrap = true,


                )


            }


        }

    }

}