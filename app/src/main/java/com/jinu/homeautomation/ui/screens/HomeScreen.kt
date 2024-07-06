package com.jinu.homeautomation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.jinu.homeautomation.ui.components.DeviceOverView

class HomeScreen(private val navController: NavController) {

    @Composable
    fun View(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Hey Maria",
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = TextUnit(
                            30f,
                            TextUnitType.Sp
                        ),
                        fontWeight = FontWeight(1000),
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.roboto_medium,
                                FontWeight.Normal,
                                style = FontStyle.Normal
                            )
                        )
                    )
                    Text(
                        text = "Welcome to Home",
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = TextUnit(
                            16f,
                            TextUnitType.Sp
                        ),
                        fontWeight = FontWeight(1000),
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.roboto_medium,
                                FontWeight.Medium,
                                style = FontStyle.Normal
                            )
                        )
                    )

                }
            }

            Whether()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Your Devices",
                    modifier = Modifier.padding(),
                    fontSize = TextUnit(
                        25f,
                        TextUnitType.Sp
                    ),
                    fontWeight = FontWeight(1000),
                    fontFamily = FontFamily(
                        Font(
                            resId = R.font.roboto_medium,
                            FontWeight.Medium,
                            style = FontStyle.Normal
                        )
                    )
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
                item { DeviceOverView() }
            }


        }
    }

    @Composable
    private fun Whether() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 20.dp, top = 20.dp),
            shape = RoundedCornerShape(30),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clode3),
                        contentDescription = "",
                        modifier = Modifier
                            .width(40.dp)
                            .height(40.dp)
                    )
                    Column {

                        Text(
                            text = "Mostly Cloudy", modifier = Modifier,
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
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Sydney,Australia", modifier = Modifier.padding(top = 5.dp),
                            fontSize = TextUnit(
                                15f,
                                TextUnitType.Sp
                            ),
                            fontWeight = FontWeight(1000),
                            fontFamily = FontFamily(
                                Font(
                                    resId = R.font.roboto_medium_normal,
                                    FontWeight.Medium,
                                    style = FontStyle.Normal
                                )
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Text(
                        text = "22°", modifier = Modifier,
                        fontSize = TextUnit(
                            50f,
                            TextUnitType.Sp
                        ),
                        fontWeight = FontWeight(1000),
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.roboto_medium,
                                FontWeight.Medium,
                                style = FontStyle.Normal
                            )
                        ), maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                }

            }
        }
    }





}
