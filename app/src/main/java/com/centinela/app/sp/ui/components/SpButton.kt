package com.centinela.app.sp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.theme.SpInk0
import com.centinela.app.sp.ui.theme.SpPanelFillSoft
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

enum class SpButtonStyle { Outline, Solid, Danger }

/** Equivalente a .btn / .btn-solid / .btn-danger de components.css */
@Composable
fun GlowButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: SpButtonStyle = SpButtonStyle.Outline,
    enabled: Boolean = true,
    leading: (@Composable () -> Unit)? = null,
) {
    val accent = spAccent()
    val (bg, border, fg) = when (style) {
        SpButtonStyle.Outline -> Triple(SpPanelFillSoft, accent.base.copy(alpha = 0.55f), SpInk0)
        SpButtonStyle.Solid -> Triple(accent.base, accent.base, Color(0xFF050708))
        SpButtonStyle.Danger -> Triple(Color(0x1FFF4526), Color(0x99FF4526), Color(0xFFFF4526))
    }
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(bg)
            .border(1.dp, border, RoundedCornerShape(4.dp))
            .clickable(enabled = enabled) { onClick() }
            .padding(PaddingValues(horizontal = 16.dp, vertical = 10.dp)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leading != null) {
            leading()
            androidx.compose.foundation.layout.Spacer(Modifier.padding(end = 8.dp))
        }
        Text(
            text = text.uppercase(),
            style = SpType.mono,
            color = if (enabled) fg else fg.copy(alpha = 0.4f),
            fontWeight = if (style == SpButtonStyle.Solid) androidx.compose.ui.text.font.FontWeight.Bold else null,
        )
    }
}
