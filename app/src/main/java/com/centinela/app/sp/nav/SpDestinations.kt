package com.centinela.app.sp.nav

import androidx.compose.ui.graphics.vector.ImageVector
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpModuleTheme

data class SpDestination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val theme: SpModuleTheme,
    val roman: String?,
    val implemented: Boolean,
)

/** Equivalente a ROUTES de app.js. Los módulos con implemented=false muestran un panel "próximamente". */
val SP_ROUTES = listOf(
    SpDestination("constitucion", "Constitución", SpIcons.Flag, SpModuleTheme.Amber, "I", implemented = true),
    SpDestination("identidad", "Identidad", SpIcons.User, SpModuleTheme.Blue, "II", implemented = true),
    SpDestination("antiidentidad", "Antiidentidad", SpIcons.Shield, SpModuleTheme.Red, "III", implemented = true),
    SpDestination("direccion", "Dirección", SpIcons.Send, SpModuleTheme.Mint, "IV", implemented = true),
    SpDestination("objetivos", "Objetivos", SpIcons.Target, SpModuleTheme.Teal, "V", implemented = false),
    SpDestination("pud", "PUD", SpIcons.Pud, SpModuleTheme.Purple, "VI", implemented = false),
    SpDestination("protocolos", "Protocolos", SpIcons.ListIcon, SpModuleTheme.Amber, "VII", implemented = false),
    SpDestination("ejecucion", "Ejecución", SpIcons.Zap, SpModuleTheme.Amber, "VIII", implemented = false),
    SpDestination("evolucion", "Evolución", SpIcons.Trend, SpModuleTheme.Neon, "IX", implemented = false),
    SpDestination("biblioteca", "Biblioteca", SpIcons.Book, SpModuleTheme.Amber, "X", implemented = false),
    SpDestination("inicio", "Inicio", SpIcons.Home, SpModuleTheme.Amber, null, implemented = false),
)

val SP_START_ROUTE = "constitucion"
