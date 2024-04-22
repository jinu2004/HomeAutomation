package com.jinu.homeautomation.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jinu.homeautomation.R

class HomeScreen(private val navController: NavController) {

    @Composable
    fun View(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Hello Maria",
                        modifier = Modifier.padding(top = 10.dp),
                        fontSize = TextUnit(
                            30f,
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
                    text = "Your Rooms",
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

                ElevatedButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Add",
                        modifier = Modifier,
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
                        )
                    )
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                item { Rooms() }
            }


        }
    }

    @Composable
    private fun Whether() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 20.dp),
            shape = RoundedCornerShape(15)
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
                            .width(60.dp)
                            .height(60.dp)
                    )
                    Column {

                        Text(
                            text = "Mostly Cloudy", modifier = Modifier,
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
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Sydney,Australia", modifier = Modifier.padding(top = 10.dp),
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                ) {
                    Column {

                        Text(
                            text = "27°C", modifier = Modifier.align(Alignment.CenterHorizontally),
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
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Sensible",
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = TextUnit(
                                10f,
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

                    Column {

                        Text(
                            text = "4%", modifier = Modifier.align(Alignment.CenterHorizontally),
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
                            )
                        )
                        Text(
                            text = "precipitation",
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = TextUnit(
                                10f,
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

                    Column {

                        Text(
                            text = "66%", modifier = Modifier.align(Alignment.CenterHorizontally),
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
                            )
                        )
                        Text(
                            text = "Humidity",
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = TextUnit(
                                10f,
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

                    Column {

                        Text(
                            text = "16 km/h",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
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
                            )
                        )
                        Text(
                            text = "Wind",
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = TextUnit(
                                10f,
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
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Rooms() {
        ElevatedCard(
            onClick = { /*TODO*/ },
            shape = RoundedCornerShape(20),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedCard(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(100.dp)
                        .height(100.dp)
                        .padding(10.dp),
                    shape = RoundedCornerShape(100),
                    border = BorderStroke(color = MaterialTheme.colorScheme.primary, width = 2.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = "Living Rooms", modifier = Modifier.padding(5.dp),
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
                    text = "07 Devices", modifier = Modifier.padding(5.dp),
                    fontSize = TextUnit(
                        12f,
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


            }
        }
    }


}
