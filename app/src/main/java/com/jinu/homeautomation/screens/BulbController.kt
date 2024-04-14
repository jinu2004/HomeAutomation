package com.jinu.homeautomation.screens

import android.animation.ArgbEvaluator
import android.content.res.Resources
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.RequestDisallowInterceptTouchEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.jinu.homeautomation.R
import io.github.ningyuv.circularseekbar.fillMinDimension
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sin

class BulbController {
    private companion object {
        const val WARMSCREEN = "warm"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun View() {
        val navController = rememberNavController()
        var warmness by remember {
            mutableFloatStateOf(1f)
        }
        var brightness by remember {
            mutableFloatStateOf(50f)
        }
        val ambientColors = listOf(
            Color.hsl(56f, 0.87f, 0.84f, 1f),
            Color.hsl(197f, 0.87f, 0.84f)
        )

        val lottyComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.bulb_animation))
        val bulbProgressAnim by animateLottieCompositionAsState(composition = lottyComposition)

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = "Bulb",
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
            NavigationBar(
                windowInsets = WindowInsets(left = 30.dp, right = 30.dp),
                containerColor = Color.Transparent
            ) {
                OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth(0.5f)) {
                    Text(text = "Warmness")
                }
                OutlinedButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Color")
                }
            }

            NavHost(navController = navController, startDestination = WARMSCREEN) {
                composable(route = WARMSCREEN) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp)
                        ) {
                            CircularSeekbarView(
                                value = warmness,
                                modifier = Modifier.fillMaxSize(),
                                onChange = { warmness = it },
                                lineWeight = 50.dp,
                                lineRoundEnd = true,
                                fullAngle = 270f,
                                startAngle = 135f,
                                drawInCircle = {
                                    drawCircle(color = interpolateColors(ambientColors[0],ambientColors[1],warmness), radius = brightness + 100)
                                }

                            )
                            FilledIconButton(
                                onClick = { /*TODO*/ }, modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(100.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Lightbulb,
                                    contentDescription = "",
                                    modifier = Modifier.size(50.dp)
                                )
                            }

                            Slider(
                                value = brightness,
                                onValueChange = { brightness = it },
                                valueRange = 0f..100f,
                                modifier = Modifier.align(
                                    Alignment.BottomCenter
                                ).padding(20.dp)
                            )
                        }

                    }


                }
            }


        }

    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun CircularSeekbarView(
        value: Float,
        onChange: (Float) -> Unit,
        modifier: Modifier = Modifier,
        steps: Int = 0,
        startAngle: Float = 180f,
        fullAngle: Float = 360f,
        activeColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
        inactiveColor: Color = MaterialTheme.colorScheme.primaryContainer,
        dotColor: Color = MaterialTheme.colorScheme.primary,
        lineWeight: Dp = 20.dp,
        lineRoundEnd: Boolean = false,
        dotRadius: Dp = 20.dp,
        dotTouchThreshold: Dp = 15.dp,
        drawInCircle: DrawScope.() -> Unit = {},
        drawDot: (DrawScope.(Offset, Float, Color, Float) -> Unit)? = null
    ) {
        val dpScale = Resources.getSystem().displayMetrics.density
        val lineWeightInPx = dpScale * lineWeight.value
        val dotRadiusInPx = dpScale * dotRadius.value
        val dotTouchThresholdInPx = dpScale * dotTouchThreshold.value
        val innerStartAngle = startAngle
        val stroke =
            Stroke(lineWeightInPx, cap = if (lineRoundEnd) StrokeCap.Round else StrokeCap.Butt)
        var sweepAngle = value * fullAngle
        if (steps > 0) {
            val perStep = fullAngle / steps
            sweepAngle = (sweepAngle / perStep).roundToInt() * perStep
        }
        var dragCenter by remember {
            mutableStateOf<Offset?>(null)
        }
        var arcCenter by remember {
            mutableStateOf<Offset?>(null)
        }
        var draggingPointerId by remember {
            mutableStateOf<Int?>(null)
        }
        val requestDisallowInterceptTouchEvent = remember {
            RequestDisallowInterceptTouchEvent()
        }
        Canvas(modifier = modifier
            .fillMinDimension()
            .pointerInteropFilter(
                requestDisallowInterceptTouchEvent
            ) {
                when (it.actionMasked) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                        val center = dragCenter ?: return@pointerInteropFilter false
                        if (Offset(it.getX(it.actionIndex), it.getY(it.actionIndex))
                                .minus(center)
                                .getDistance() <= dotRadiusInPx + dotTouchThresholdInPx
                        ) {
                            draggingPointerId = it.getPointerId(it.actionIndex)
                            requestDisallowInterceptTouchEvent(true)
                        }
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val center = arcCenter ?: return@pointerInteropFilter false
                        val pointerId = draggingPointerId ?: return@pointerInteropFilter true
                        val pointerIndex = it.findPointerIndex(pointerId)
                        val pointerOffset = Offset(it.getX(pointerIndex), it.getY(pointerIndex))
                        val v = pointerOffset.minus(center)
                        if (v.getDistance() == 0f) return@pointerInteropFilter true
                        var nextSweepAngle =
                            (atan(v.y / v.x) / PI * 180 - innerStartAngle).toFloat()
                        if (v.x < 0) {
                            nextSweepAngle += 180
                        }
                        while (nextSweepAngle < 0) {
                            nextSweepAngle += 360
                        }
                        while (nextSweepAngle > 360) {
                            nextSweepAngle -= 360
                        }
                        nextSweepAngle =
                            if (sweepAngle > fullAngle * 3 / 4 && (nextSweepAngle < fullAngle / 2 || nextSweepAngle > fullAngle)) {
                                fullAngle
                            } else if (sweepAngle < fullAngle * 1 / 4 && nextSweepAngle > fullAngle / 2) {
                                0f
                            } else {
                                nextSweepAngle
                            }
                        if (steps > 0) {
                            val perStep = fullAngle / steps
                            val step = (nextSweepAngle / perStep).roundToInt()
                            nextSweepAngle = step * perStep
                        }
                        if (nextSweepAngle / fullAngle != value) {
                            onChange(nextSweepAngle / fullAngle)
                        }
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                        if (it.getPointerId(it.actionIndex) == draggingPointerId) {
                            draggingPointerId = null
                            requestDisallowInterceptTouchEvent(false)
                        }
                    }

                    MotionEvent.ACTION_CANCEL -> {
                        draggingPointerId = null
                    }

                    else -> return@pointerInteropFilter false
                }
                true
            }) {
            val radius = size.minDimension / 2.0f - max(lineWeightInPx / 2, dotRadiusInPx)
            val brush = Brush.horizontalGradient(
                listOf(
                    Color.hsl(56f, 0.87f, 0.84f, 1f),
                    Color.hsl(197f, 0.87f, 0.84f)
                )
            )

            drawArc(
                brush = brush,
                startAngle = innerStartAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = stroke
            )
            drawArc(
                brush = brush,
                startAngle = innerStartAngle + sweepAngle,
                sweepAngle = fullAngle - sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = stroke
            )
            val dotAngle = (innerStartAngle + sweepAngle)
            val dotCenter = Offset(
                center.x + cos((dotAngle / 180f * PI).toFloat()) * radius,
                center.y + sin((dotAngle / 180f * PI).toFloat()) * radius
            )
            this.drawInCircle()
            if (drawDot != null) {
                this.drawDot(dotCenter, dotAngle, dotColor, dotRadiusInPx)
            } else {
                drawCircle(
                    color = dotColor,
                    center = dotCenter,
                    radius = dotRadiusInPx
                )
            }
            dragCenter = dotCenter
            arcCenter = center
        }
    }

    fun interpolateColors(startColor: Color, endColor: Color, fraction: Float): Color {
        val red = (startColor.red + fraction * (endColor.red - startColor.red)).coerceIn(0f, 1f)
        val green = (startColor.green + fraction * (endColor.green - startColor.green)).coerceIn(0f, 1f)
        val blue = (startColor.blue + fraction * (endColor.blue - startColor.blue)).coerceIn(0f, 1f)
        return Color(red, green, blue)
    }
}