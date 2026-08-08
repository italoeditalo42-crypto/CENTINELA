package com.centinela.app.sp.ui.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.theme.SpType

/** Barras verticales simples con rejilla, equivalente a barChart() de components.js */
@Composable
fun BarChart(
    labels: List<String>,
    values: List<Float>,
    modifier: Modifier = Modifier,
    max: Float = 100f,
    height: Dp = 160.dp,
    color: Color,
) {
    val n = (labels.size).coerceAtLeast(1)
    val textMeasurer = rememberTextMeasurer()
    val hairline = Color(0xFF8CAFBE).copy(alpha = 0.14f)
    val inkDim = Color(0xFF7D939C)

    Canvas(modifier = modifier.fillMaxWidth().height(height)) {
        val w = this.size.width
        val h = this.size.height
        val gap = 10f
        val barW = (w - gap * (n + 1)) / n
        val baseline = h - 24f

        listOf(0.25f, 0.5f, 0.75f, 1f).forEach { f ->
            val y = baseline - f * (h - 28f)
            drawLine(
                hairline, Offset(0f, y), Offset(w, y), strokeWidth = 1f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(2f, 3f), 0f)
            )
        }
        drawLine(hairline, Offset(0f, baseline), Offset(w, baseline), strokeWidth = 1f)

        values.forEachIndexed { i, v ->
            val barH = ((v.coerceAtMost(max) / max) * (h - 28f)).coerceAtLeast(2f)
            val x = gap + i * (barW + gap)
            val y = baseline - barH
            drawRect(color.copy(alpha = 0.8f), topLeft = Offset(x, y), size = androidx.compose.ui.geometry.Size(barW, barH))
            drawRect(color, topLeft = Offset(x, y), size = androidx.compose.ui.geometry.Size(barW, barH), style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1f))

            val valueText = textMeasurer.measure(v.toInt().toString(), style = SpType.monoSm.copy(color = inkDim))
            drawText(valueText, topLeft = Offset(x + barW / 2f - valueText.size.width / 2f, (y - 14f).coerceAtLeast(2f)))

            val labelText = textMeasurer.measure(labels.getOrElse(i) { "" }, style = SpType.monoSm.copy(color = inkDim))
            drawText(labelText, topLeft = Offset(x + barW / 2f - labelText.size.width / 2f, h - 20f))
        }
    }
}
