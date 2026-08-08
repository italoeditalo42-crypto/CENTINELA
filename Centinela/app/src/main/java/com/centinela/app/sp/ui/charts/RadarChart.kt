package com.centinela.app.sp.ui.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.theme.SpType
import kotlin.math.cos
import kotlin.math.sin

/** Radar chart genérico, equivalente a radar() de components.js */
@Composable
fun RadarChart(
    labels: List<String>,
    values: List<Float>,
    modifier: Modifier = Modifier,
    max: Float = 100f,
    size: Dp = 190.dp,
    color: Color,
    fill: Boolean = true,
) {
    val n = labels.size
    if (n == 0) return
    val textMeasurer = rememberTextMeasurer()
    val gridColor = Color(0xFF4C626B)
    val labelStyle = SpType.monoSm.copy(color = Color(0xFF7D939C), textAlign = TextAlign.Center)

    Canvas(modifier = modifier.size(size)) {
        val sizePx = this.size.width
        val pad = sizePx * 0.30f
        val cx = sizePx / 2f + pad / 2f
        val cy = sizePx / 2f + pad / 2f - sizePx * 0.02f
        val radius = sizePx * 0.34f

        fun point(i: Int, f: Float): Offset {
            val a = -Math.PI / 2 + i * (2 * Math.PI / n)
            return Offset(
                (cx + cos(a) * radius * f).toFloat(),
                (cy + sin(a) * radius * f).toFloat(),
            )
        }

        // rejilla (3 anillos)
        listOf(0.33f, 0.66f, 1f).forEach { f ->
            val path = Path()
            for (i in 0 until n) {
                val p = point(i, f)
                if (i == 0) path.moveTo(p.x, p.y) else path.lineTo(p.x, p.y)
            }
            path.close()
            drawPath(path, color = gridColor.copy(alpha = 0.35f), style = Stroke(width = 1f))
        }
        // ejes
        for (i in 0 until n) {
            val p = point(i, 1f)
            drawLine(gridColor.copy(alpha = 0.3f), Offset(cx, cy), p, strokeWidth = 1f)
        }
        // polígono de datos
        val dataPath = Path()
        for (i in 0 until n) {
            val f = (values.getOrElse(i) { 0f } / max).coerceIn(0f, 1f)
            val p = point(i, f)
            if (i == 0) dataPath.moveTo(p.x, p.y) else dataPath.lineTo(p.x, p.y)
        }
        dataPath.close()
        if (fill) drawPath(dataPath, color = color.copy(alpha = 0.22f))
        drawPath(dataPath, color = color, style = Stroke(width = 1.6f * density))

        // etiquetas
        for (i in 0 until n) {
            val p = point(i, 1.30f)
            val measured = textMeasurer.measure(labels[i], style = labelStyle)
            drawText(
                textLayoutResult = measured,
                topLeft = Offset(p.x - measured.size.width / 2f, p.y - measured.size.height / 2f)
            )
        }
    }
}
