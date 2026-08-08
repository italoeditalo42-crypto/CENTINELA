package com.centinela.app.sp.nav

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.components.SpIcon
import com.centinela.app.sp.ui.theme.SpBg1
import com.centinela.app.sp.ui.theme.SpHairline
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.OctagonCutShape
import com.centinela.app.sp.ui.theme.SpCuts

/** Equivalente a .sidebar de layout.css: columna fija de 116dp con navlinks hexagonales. */
@Composable
fun SidebarRail(
    currentRoute: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(96.dp)
            .fillMaxHeight()
            .background(SpBg1)
            .border(androidx.compose.foundation.BorderStroke(1.dp, SpHairline)),
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(vertical = 14.dp, horizontal = 8.dp),
        ) {
            items(SP_ROUTES) { dest ->
                NavRailItem(
                    dest = dest,
                    selected = dest.route == currentRoute,
                    onClick = { onSelect(dest.route) },
                )
            }
        }
    }
}

@Composable
private fun NavRailItem(dest: SpDestination, selected: Boolean, onClick: () -> Unit) {
    val accent = dest.theme.accent
    val borderColor = if (selected) accent.base else SpHairline
    val bg = if (selected) accent.base.copy(alpha = 0.14f) else Color.Transparent
    val iconTint = if (selected) accent.base else SpInk2
    Column(
        modifier = Modifier
            .clip(OctagonCutShape(SpCuts.md))
            .background(bg)
            .border(1.dp, borderColor, OctagonCutShape(SpCuts.md))
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SpIcon(icon = dest.icon, tint = iconTint, size = 20.dp)
        Spacer(Modifier.height(4.dp))
        Text(
            text = dest.label.uppercase(),
            style = SpType.navLabel,
            color = iconTint,
            textAlign = TextAlign.Center,
            maxLines = 2,
        )
        if (!dest.implemented) {
            Spacer(Modifier.height(2.dp))
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(3.dp)
                    .clip(androidx.compose.foundation.shape.CircleShape)
                    .background(SpInk2.copy(alpha = 0.6f))
            )
        }
    }
}
