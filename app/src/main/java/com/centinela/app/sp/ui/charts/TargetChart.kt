package com.centinela.app.sp.ui.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/** Retícula con marcador de progreso, equivalente a targetGauge() de components.js */
@Composable
fun TargetChart(
    value: Float,
    modifier: Modifier = Modifier,
    size: Dp = 90.dp,
    color: Color,
) {
    val clamped = value.coerceIn(0f, 1f)
    Canvas(modifier = modifier.size(size)) {
        val s = this.size.width
        val cx = s / 2f
        val cy = s / 2f
        listOf(0.92f, 0.68f, 0.44f).forEach { f ->
            drawCircle(color.copy(alpha = 0.35f), radius = s / 2f * f, center = Offset(cx, cy), style = Stroke(width = 1f))
        }
        drawLine(color.copy(alpha = 0.25f), Offset(4f, cy), Offset(s - 4f, cy), strokeWidth = 1f)
        drawLine(color.copy(alpha = 0.25f), Offset(cx, 4f), Offset(cx, s - 4f), strokeWidth = 1f)
        drawCircle(color, radius = 3f, center = Offset(cx, cy))

        val angle = Math.toRadians((-90 + clamped * 360).toDouble())
        val mr = s * 0.32f
        val mx = cx + mr * cos(angle).toFloat()
        val my = cy + mr * sin(angle).toFloat()
        drawCircle(color, radius = 4.5f, center = Offset(mx, my))
    }
}
