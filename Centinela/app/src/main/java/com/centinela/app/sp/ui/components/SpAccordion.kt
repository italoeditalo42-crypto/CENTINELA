package com.centinela.app.sp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpInk1
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

/** Equivalente a .acc / .acc-head / .acc-body de components.css */
@Composable
fun SpAccordionItem(
    tag: String,
    label: String,
    body: String,
    modifier: Modifier = Modifier,
    initiallyOpen: Boolean = false,
) {
    var open by remember { mutableStateOf(initiallyOpen) }
    val accent = spAccent()
    val borderAlpha = if (open) 0.55f else 0.30f
    val rotation by androidx.compose.animation.core.animateFloatAsState(if (open) 180f else 0f, label = "chev")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .background(Color(0xB8040E14))
            .border(1.dp, accent.base.copy(alpha = borderAlpha), RoundedCornerShape(2.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { open = !open }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = tag,
                style = SpType.monoSm,
                color = accent.base,
                modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .background(accent.base.copy(alpha = 0.10f))
                    .border(1.dp, accent.base.copy(alpha = 0.55f), RoundedCornerShape(2.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp),
            )
            Spacer(Modifier.width(14.dp))
            Text(text = label, style = SpType.label, color = Color(0xFFF2F6F7), modifier = Modifier.weight(1f))
            SpIcon(
                icon = SpIcons.ChevDown,
                tint = accent.base,
                size = 16.dp,
                modifier = Modifier.rotate(rotation),
            )
        }
        AnimatedVisibility(visible = open, enter = fadeIn() + expandVertically(), exit = fadeOut() + shrinkVertically()) {
            Text(
                text = body,
                style = SpType.body,
                color = SpInk1,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            )
        }
    }
}
