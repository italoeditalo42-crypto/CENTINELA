package com.centinela.app.sp.ui.theme

import androidx.compose.ui.graphics.Color

// ---- Base / estructura ----
val SpVoid = Color(0xFF000000)
val SpBg0 = Color(0xFF02060A)
val SpBg1 = Color(0xFF040B10)
val SpBg2 = Color(0xFF071319)
val SpPanelFill = Color(0xB8040E14)      // rgba(4,14,20,.72)
val SpPanelFillSoft = Color(0x73040E14)  // rgba(4,14,20,.45)
val SpHairline = Color(0x248CAFBE)       // rgba(140,175,190,.14)

val SpInk0 = Color(0xFFF2F6F7)
val SpInk1 = Color(0xFFB9C8CE)
val SpInk2 = Color(0xFF7D939C)
val SpInk3 = Color(0xFF4C626B)

// ---- Acentos por módulo ----
data class SpAccent(val base: Color, val dim: Color, val glow: Color)

val SpAmber = SpAccent(Color(0xFFFFAB13), Color(0xFF8A5A10), Color(0x59FFAB13))
val SpCyan = SpAccent(Color(0xFF00C2E0), Color(0xFF0A5C6B), Color(0x5900C2E0))
val SpBlue = SpAccent(Color(0xFF0A9CFF), Color(0xFF0A4680), Color(0x590A9CFF))
val SpRed = SpAccent(Color(0xFFFF4526), Color(0xFF7A2015), Color(0x59FF4526))
val SpMint = SpAccent(Color(0xFF00FFB3), Color(0xFF0A6B4C), Color(0x5200FFB3))
val SpTeal = SpAccent(Color(0xFF00FCF5), Color(0xFF0A6B68), Color(0x5200FCF5))
val SpPurple = SpAccent(Color(0xFF8926FA), Color(0xFF4A1690), Color(0x618926FA))
val SpNeon = SpAccent(Color(0xFF00E5FF), Color(0xFF0A5C70), Color(0x5900E5FF))

// ---- Semántico ----
val SpOk = Color(0xFF3DDC84)
val SpOkGlow = Color(0x593DDC84)
val SpWarn = Color(0xFFFFAB13)
val SpDanger = Color(0xFFFF4526)
val SpInfo = Color(0xFF0A9CFF)

enum class SpModuleTheme(val accent: SpAccent) {
    Amber(SpAmber), Cyan(SpCyan), Blue(SpBlue), Red(SpRed),
    Mint(SpMint), Teal(SpTeal), Purple(SpPurple), Neon(SpNeon)
}
