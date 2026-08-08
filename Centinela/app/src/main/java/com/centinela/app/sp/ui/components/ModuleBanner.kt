package com.centinela.app.sp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.theme.CornerCutShape
import com.centinela.app.sp.ui.theme.SpBg0
import com.centinela.app.sp.ui.theme.SpCuts
import com.centinela.app.sp.ui.theme.SpInk0
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

/** Equivalente a .module-banner de components.css */
@Composable
fun ModuleBanner(
    title: String,
    modifier: Modifier = Modifier,
    roman: String? = null,
    sub: String? = null,
    glyph: (@Composable () -> Unit)? = null,
) {
    val accent = spAccent()
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(androidx.compose.foundation.shape.RoundedCornerShape(2.dp))
            .background(
                Brush.linearGradient(
                    listOf(accent.base.copy(alpha = 0.12f), SpBg0)
                )
            )
            .border(1.dp, accent.base.copy(alpha = 0.55f), androidx.compose.foundation.shape.RoundedCornerShape(2.dp))
            .padding(horizontal = 22.dp, vertical = 20.dp),
    ) {
        Row(verticalAlignment = Alignment.Top, horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.Top, modifier = Modifier.weight(1f)) {
                if (roman != null) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CornerCutShape(SpCuts.md))
                            .background(
                                Brush.verticalGradient(listOf(accent.base.copy(alpha = 0.18f), Color.Transparent, SpBg0))
                            )
                            .border(1.dp, accent.base, CornerCutShape(SpCuts.md)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(roman, style = SpType.roman, color = accent.base)
                    }
                    Spacer(Modifier.width(14.dp))
                }
                Column {
                    Text(
                        text = title,
                        style = SpType.bannerTitle.copy(
                            brush = Brush.horizontalGradient(listOf(SpInk0, accent.base))
                        ),
                    )
                    if (sub != null) {
                        Spacer(Modifier.height(6.dp))
                        Text(sub, style = SpType.mono, color = SpInk2)
                    }
                }
            }
            if (glyph != null) {
                Box(modifier = Modifier.size(64.dp)) { glyph() }
            }
        }
    }
}
