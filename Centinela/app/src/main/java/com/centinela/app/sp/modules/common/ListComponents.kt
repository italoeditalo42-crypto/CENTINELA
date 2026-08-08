package com.centinela.app.sp.modules.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.components.SpIcon
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpInk1
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

/** Equivalente a .bullet-list / .min-item (check + texto) */
@Composable
fun CheckList(items: List<String>, modifier: Modifier = Modifier) {
    val accent = spAccent()
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(9.dp)) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.Top) {
                SpIcon(icon = SpIcons.Check, tint = accent.base, size = 14.dp, modifier = Modifier.padding(top = 3.dp))
                Spacer(Modifier.width(9.dp))
                Text(item, style = SpType.body, color = SpInk1)
            }
        }
    }
}

/** Equivalente a .dash-list (guión + texto, más compacto) */
@Composable
fun DashList(items: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(7.dp)) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.Top) {
                Text("—", style = SpType.body, color = SpInk2, modifier = Modifier.padding(end = 8.dp))
                Text(item, style = SpType.body, color = SpInk1)
            }
        }
    }
}

/** Equivalente a .x-list (marca x + texto, para señales de alerta) */
@Composable
fun XList(items: List<String>, modifier: Modifier = Modifier, color: androidx.compose.ui.graphics.Color = SpInk1) {
    val accent = spAccent()
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(9.dp)) {
        items.forEach { item ->
            Row(verticalAlignment = Alignment.Top) {
                SpIcon(icon = SpIcons.X, tint = accent.base, size = 13.dp, modifier = Modifier.padding(top = 4.dp))
                Spacer(Modifier.width(9.dp))
                Text(item, style = SpType.body, color = color)
            }
        }
    }
}

/** Equivalente a .steps-list (numerado, para "cómo recupero el control") */
@Composable
fun StepsList(items: List<String>, modifier: Modifier = Modifier) {
    val accent = spAccent()
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.forEachIndexed { i, item ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "${i + 1}", style = SpType.mono, color = accent.base, fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(18.dp),
                )
                Text(item, style = SpType.body, color = SpInk1)
            }
        }
    }
}

/** Equivalente a .tool-chips > .chip */
@Composable
fun ChipRow(items: List<String>, modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalArrangement = Arrangement.spacedBy(7.dp),
    ) {
        items.forEach { text ->
            Text(
                text = text,
                style = SpType.monoSm,
                color = SpInk1,
                modifier = Modifier
                    .border(1.dp, SpInk2.copy(alpha = 0.35f), androidx.compose.foundation.shape.RoundedCornerShape(50))
                    .padding(horizontal = 11.dp, vertical = 6.dp),
            )
        }
    }
}

/** Equivalente a segmentBar() de components.js — barra de N segmentos discretos */
@Composable
fun SegmentBar(pct: Int, modifier: Modifier = Modifier, segments: Int = 10) {
    val accent = spAccent()
    val filled = kotlin.math.round((pct / 100f) * segments).toInt()
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        repeat(segments) { i ->
            val on = i < filled
            Row(
                modifier = Modifier
                    .weight(1f)
                    .height(6.dp)
                    .then(
                        if (on) Modifier.background(accent.base)
                        else Modifier.border(1.dp, SpInk2.copy(alpha = 0.30f))
                    )
            ) {}
        }
    }
}
