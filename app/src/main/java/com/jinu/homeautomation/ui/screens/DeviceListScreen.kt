package com.jinu.homeautomation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jinu.homeautomation.R

class DeviceListScreen(private val navController: NavController) {

    @Composable
    fun View(modifier: Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(start = 10.dp)) {
                    Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "")
                }
                Text(
                    text = "Living Rooms", modifier = Modifier.fillMaxWidth(0.75f),
                    fontSize = TextUnit(
                        20f,
                        TextUnitType.Sp
                    ),
                    fontWeight = FontWeight(1000),
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.roboto_medium,
                            FontWeight.Medium,
                            style = FontStyle.Normal
                        )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

            }


            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                item { Devices() }
            }

        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Devices() {
        ElevatedCard(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(20),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.smart_light),
                    contentDescription = "",
                    modifier = Modifier
                        .size(70.dp)
                        .padding(start = 20.dp, top = 10.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = "Bulb", modifier = Modifier.padding(start = 20.dp),
                    fontSize = TextUnit(
                        18f,
                        TextUnitType.Sp
                    ),
                    fontWeight = FontWeight(1000),
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.roboto_medium,
                            FontWeight.Medium,
                            style = FontStyle.Normal
                        )
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var checked by remember {
                        mutableStateOf(false)
                    }
                    Text(
                        text = "Bulb",
                        fontSize = TextUnit(
                            18f,
                            TextUnitType.Sp
                        ),
                        fontWeight = FontWeight(1000),
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.roboto_medium,
                                FontWeight.Medium,
                                style = FontStyle.Normal
                            )
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )
                    Switch(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        },
                        thumbContent = if (checked) {
                            {
                                Icon(
                                    imageVector = Icons.Filled.PowerSettingsNew,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                )
                            }
                        } else {
                            null
                        }
                    )
                }


            }

        }
    }

}