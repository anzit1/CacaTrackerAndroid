package com.example.cacatrackermobileapp.utils

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Retirado de GITHUB
// https://github.com/developerchunk/Custom-Pie-Chart-Jetpack-Compose/tree/main
//

@Composable
fun PieChart(
    data: Map<String, Int>,
    modifier: Modifier = Modifier,
    chartBarWidthRatio: Float = 0.25f,
    animDuration: Int = 1000
) {
    val totalSum = data.values.sum()
    if (totalSum == 0) {

        Text(
            text = "No hay datos disponibles.",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
        return
    }

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val size = maxWidth.coerceAtMost(maxHeight)
        val radiusOuter = size / 2
        val chartBarWidth = radiusOuter * chartBarWidthRatio

        val floatValue = data.values.map { 360f * it / totalSum }

        val colors = listOf(
            Color(0xFFCEC658),
            Color(0xFF3F7DE0),
            Color(0xFFE7514E),
            Color(0xFF6EE050),
            Color(0xFF90A4AE)
        )

        var animationPlayed by remember { mutableStateOf(false) }
        var lastValue = 0f

        val animateSize by animateFloatAsState(
            targetValue = if (animationPlayed) size.value else 0f,
            animationSpec = tween(animDuration, easing = LinearOutSlowInEasing)
        )

        val animateRotation by animateFloatAsState(
            targetValue = if (animationPlayed) 990f else 0f,
            animationSpec = tween(animDuration, easing = LinearOutSlowInEasing)
        )

        LaunchedEffect(Unit) {
            animationPlayed = true
        }

        Box(
            modifier = Modifier.size(animateSize.dp),
            contentAlignment = Alignment.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(size)
                    .rotate(animateRotation)
            ) {
                floatValue.forEachIndexed { index, value ->
                    drawArc(
                        color = colors[index % colors.size],
                        startAngle = lastValue,
                        sweepAngle = value,
                        useCenter = false,
                        style = Stroke(chartBarWidth.toPx(), cap = StrokeCap.Butt)
                    )
                    lastValue += value
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(chartBarWidth / 1.5f)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            data.entries.forEachIndexed { index, entry ->
                Text(
                    text = "${entry.key}: ${entry.value}",
                    color = colors[index % colors.size],
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
            }
        }
    }
}