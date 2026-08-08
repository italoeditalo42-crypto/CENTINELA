package com.centinela.app.sp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.theme.spAccent

/** Equivalente a .progress > i de components.css */
@Composable
fun SpProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 8.dp,
    trackColor: Color = Color.White.copy(alpha = 0.04f),
) {
    val accent = spAccent()
    val clamped = progress.coerceIn(0f, 1f)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(99.dp))
            .background(trackColor)
            .border(1.dp, Color(0x248CAFBE), RoundedCornerShape(99.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(clamped)
                .clip(RoundedCornerShape(99.dp))
                .background(Brush.horizontalGradient(listOf(accent.base.copy(alpha = 0.85f), accent.base)))
        )
    }
}

/** Fila con barra + porcentaje, equivalente a .progress-row */
@Composable
fun SpProgressRow(progress: Float, modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SpProgressBar(progress = progress, modifier = Modifier.weight(1f))
        androidx.compose.foundation.layout.Spacer(Modifier.width(10.dp))
        androidx.compose.material3.Text(
            text = "${(progress.coerceIn(0f, 1f) * 100).toInt()}%",
            style = com.centinela.app.sp.ui.theme.SpType.mono,
            color = com.centinela.app.sp.ui.theme.SpInk1,
            modifier = Modifier.width(40.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.End,
        )
    }
}
