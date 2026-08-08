package com.centinela.app.sp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.theme.SpPanelFill
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

/**
 * Equivalente a .panel de components.css: fondo con leve degradado del acento,
 * borde mezclado con el acento y opción de glow (panel-glow).
 */
@Composable
fun AngularPanel(
    modifier: Modifier = Modifier,
    glow: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(20.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    val accent = spAccent()
    val borderColor = if (glow) accent.base.copy(alpha = 0.65f) else accent.base.copy(alpha = 0.40f)
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(2.dp))
            .background(
                Brush.verticalGradient(
                    listOf(accent.base.copy(alpha = 0.06f), SpPanelFill, SpPanelFill)
                )
            )
            .border(1.dp, borderColor, RoundedCornerShape(2.dp))
            .padding(contentPadding),
        content = content
    )
}

/** Título de panel: icono opcional + texto en mayúsculas con el acento activo + línea inferior. */
@Composable
fun PanelTitle(
    text: String,
    modifier: Modifier = Modifier,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
) {
    val accent = spAccent()
    Column(modifier = modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            if (leading != null) {
                leading()
                Spacer(Modifier.width(8.dp))
            }
            Text(
                text = text.uppercase(),
                style = SpType.panelTitle,
                color = accent.base,
                modifier = Modifier.weight(1f),
            )
            trailing?.invoke()
        }
        Spacer(Modifier.height(6.dp))
        HorizontalDivider(color = accent.base.copy(alpha = 0.22f), thickness = 1.dp)
        Spacer(Modifier.height(6.dp))
    }
}
