package com.centinela.app.sp.ui.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.theme.SpType
import kotlin.math.min

/** Gauge circular 0..1, equivalente a donut() de components.js */
@Composable
fun DonutChart(
    value: Float,
    modifier: Modifier = Modifier,
    size: Dp = 110.dp,
    strokeWidth: Dp = 9.dp,
    color: Color,
    trackColor: Color = Color.White.copy(alpha = 0.08f),
    label: String? = null,
    sub: String? = null,
) {
    val clamped = value.coerceIn(0f, 1f)
    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(size)) {
            val stroke = strokeWidth.toPx()
            val r = min(this.size.width, this.size.height) / 2f - stroke / 2f
            val topLeft = androidx.compose.ui.geometry.Offset(
                this.size.width / 2f - r, this.size.height / 2f - r
            )
            val arcSize = androidx.compose.ui.geometry.Size(r * 2f, r * 2f)
            drawArc(
                color = trackColor, startAngle = 0f, sweepAngle = 360f, useCenter = false,
                topLeft = topLeft, size = arcSize, style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
            if (clamped > 0f) {
                drawArc(
                    color = color, startAngle = -90f, sweepAngle = 360f * clamped, useCenter = false,
                    topLeft = topLeft, size = arcSize, style = Stroke(width = stroke, cap = StrokeCap.Round)
                )
            }
        }
        if (label != null || sub != null) {
            androidx.compose.foundation.layout.Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (label != null) Text(label, style = SpType.statValue, color = Color(0xFFF2F6F7))
                if (sub != null) Text(sub, style = SpType.monoSm, color = Color(0xFF7D939C))
            }
        }
    }
}
