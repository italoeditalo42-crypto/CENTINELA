package com.centinela.app.sp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent
import androidx.compose.ui.unit.dp

enum class SpPillStyle { Accent, Ok, Muted, Danger }

/** Equivalente a .pill / .pill-accent / .pill-ok / .pill-muted / .pill-danger */
@Composable
fun SpPill(text: String, modifier: Modifier = Modifier, style: SpPillStyle = SpPillStyle.Accent) {
    val accent = spAccent()
    val color = when (style) {
        SpPillStyle.Accent -> accent.base
        SpPillStyle.Ok -> Color(0xFF3DDC84)
        SpPillStyle.Muted -> Color(0xFF4C626B)
        SpPillStyle.Danger -> Color(0xFFFF4526)
    }
    Text(
        text = text.uppercase(),
        style = SpType.monoSm,
        color = color,
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .border(1.dp, color, RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .padding(PaddingValues(horizontal = 12.dp, vertical = 5.dp)),
    )
}
