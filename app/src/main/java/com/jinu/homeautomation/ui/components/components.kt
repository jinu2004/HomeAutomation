package com.jinu.homeautomation.ui.components

import android.content.res.Resources
import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.RequestDisallowInterceptTouchEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.jinu.homeautomation.R
import io.github.ningyuv.circularseekbar.fillMinDimension
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun AlertDialogPreBuild(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}



@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularSeekbarView(
    value: Float,
    onChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    steps: Int = 0,
    startAngle: Float = 180f,
    fullAngle: Float = 360f,
    dotColor: Color = MaterialTheme.colorScheme.primary,
    lineWeight: Dp = 20.dp,
    lineRoundEnd: Boolean = false,
    dotRadius: Dp = 20.dp,
    dotTouchThreshold: Dp = 15.dp,
    drawInCircle: DrawScope.() -> Unit = {},
    drawDot: (DrawScope.(Offset, Float, Color, Float) -> Unit)? = null
) {
    val color = MaterialTheme.colorScheme.primary
    val dpScale = Resources.getSystem().displayMetrics.density
    val lineWeightInPx = dpScale * lineWeight.value
    val dotRadiusInPx = dpScale * dotRadius.value
    val dotTouchThresholdInPx = dpScale * dotTouchThreshold.value
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
                        (atan(v.y / v.x) / PI * 180 - startAngle).toFloat()
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
        val paint = Brush.horizontalGradient(
            listOf(
                Color.hsl(56f, 0.87f, 0.84f, 1f),
                Color.hsl(197f, 0.87f, 0.84f)
            )
        )

        drawArc(
            color = color,
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = stroke
        )
        drawArc(
            color = color,
            startAngle = startAngle + sweepAngle,
            sweepAngle = fullAngle - sweepAngle,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = stroke
        )
        val dotAngle = (startAngle + sweepAngle)
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


@Composable
fun DeviceOverView() {
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

            Row(Modifier.fillMaxSize()) {

                OutlinedCard(
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp)
                        .padding(15.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(100),
                    border = BorderStroke(width = 0.dp, color = Color.Transparent)


                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fan),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Text(
                    text = "Fan", modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
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
            }


            Row(
                Modifier
                    .fillMaxSize()
                    .padding(10.dp)) {
                FloatingActionButton(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.CenterVertically),
                    onClick = { },
                    shape = RoundedCornerShape(100),
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 2.dp)
                ) {
                    Icon(Icons.Outlined.PowerSettingsNew, contentDescription = "")
                }
            }


        }
    }
}