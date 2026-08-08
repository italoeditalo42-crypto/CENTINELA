package com.centinela.app.sp.modules.placeholder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.nav.SpDestination
import com.centinela.app.sp.ui.components.ModuleBanner
import com.centinela.app.sp.ui.components.AngularPanel
import com.centinela.app.sp.ui.components.SpIcon
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpInk1
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

@Composable
fun PlaceholderScreen(dest: SpDestination) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ModuleBanner(
            title = dest.label,
            roman = dest.roman,
            sub = "MÓDULO ${dest.roman ?: ""} · PENDIENTE DE INTEGRACIÓN",
        )
        AngularPanel(modifier = Modifier.weight(1f).fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                val accent = spAccent()
                SpIcon(icon = dest.icon, tint = accent.base, size = 40.dp)
                androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 12.dp))
                Text("PRÓXIMAMENTE", style = SpType.panelTitle, color = accent.base)
                androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 6.dp))
                Text(
                    "Este módulo se integra en la siguiente fase del plan.",
                    style = SpType.body,
                    color = SpInk1,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
            }
        }
    }
}
