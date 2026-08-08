package com.centinela.app.sp.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.centinela.app.R

/*
 * 4 familias (archivos ya incluidos en app/src/main/res/font/):
 *   Michroma      → michroma_regular.ttf (única variante: la fuente solo existe en un peso)
 *   Oxanium       → oxanium_light/regular/medium/semibold/bold/extrabold.ttf
 *   Chakra Petch  → chakra_petch_light/regular/medium/semibold/bold.ttf
 *   Martian Mono  → martian_mono_regular.ttf (única variante)
 */

val MichromaFamily = FontFamily(
    Font(R.font.michroma_regular, FontWeight.Normal),
)

val OxaniumFamily = FontFamily(
    Font(R.font.oxanium_light, FontWeight.Light),
    Font(R.font.oxanium_regular, FontWeight.Normal),
    Font(R.font.oxanium_medium, FontWeight.Medium),
    Font(R.font.oxanium_semibold, FontWeight.SemiBold),
    Font(R.font.oxanium_bold, FontWeight.Bold),
    Font(R.font.oxanium_extrabold, FontWeight.ExtraBold),
)

val ChakraPetchFamily = FontFamily(
    Font(R.font.chakra_petch_light, FontWeight.Light),
    Font(R.font.chakra_petch_regular, FontWeight.Normal),
    Font(R.font.chakra_petch_medium, FontWeight.Medium),
    Font(R.font.chakra_petch_semibold, FontWeight.SemiBold),
    Font(R.font.chakra_petch_bold, FontWeight.Bold),
)

val MartianMonoFamily = FontFamily(
    Font(R.font.martian_mono_regular, FontWeight.Normal),
)

object SpType {
    // ---- Michroma — solo el H1 de banner de módulo (1-2 palabras, mayúsculas) ----
    val bannerTitle = TextStyle(
        fontFamily = MichromaFamily, fontWeight = FontWeight.Normal,
        fontSize = 25.sp, lineHeight = 30.sp, letterSpacing = 0.02.em,
    )

    // ---- Oxanium — resto del display: panel-title, roman numerals, stat-value, quick-btn ----
    val panelTitle = TextStyle(
        fontFamily = OxaniumFamily, fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp, letterSpacing = 0.08.em,
    )
    val roman = TextStyle(
        fontFamily = OxaniumFamily, fontWeight = FontWeight.SemiBold, fontSize = 20.sp,
    )
    val statValue = TextStyle(
        fontFamily = OxaniumFamily, fontWeight = FontWeight.Bold, fontSize = 28.sp,
    )
    val quickBtnLabel = TextStyle(
        fontFamily = OxaniumFamily, fontWeight = FontWeight.Medium,
        fontSize = 12.5.sp, letterSpacing = 0.03.em,
    )

    // ---- Chakra Petch — cuerpo de texto ----
    val body = TextStyle(
        fontFamily = ChakraPetchFamily, fontWeight = FontWeight.Normal,
        fontSize = 14.5.sp, lineHeight = 23.sp,
    )
    val bodyLarge = TextStyle(
        fontFamily = ChakraPetchFamily, fontWeight = FontWeight.Normal,
        fontSize = 15.sp, lineHeight = 24.sp,
    )
    val label = TextStyle(
        fontFamily = ChakraPetchFamily, fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    )

    // ---- Martian Mono — labels técnicos, badges, tags, nav ----
    val mono = TextStyle(
        fontFamily = MartianMonoFamily, fontWeight = FontWeight.Normal,
        fontSize = 11.5.sp, letterSpacing = 0.04.em,
    )
    val monoSm = TextStyle(
        fontFamily = MartianMonoFamily, fontWeight = FontWeight.Normal,
        fontSize = 10.sp, letterSpacing = 0.05.em,
    )
    val monoLabel = TextStyle(
        fontFamily = MartianMonoFamily, fontWeight = FontWeight.Normal,
        fontSize = 10.5.sp, letterSpacing = 0.05.em,
    )
    val navLabel = TextStyle(
        fontFamily = MartianMonoFamily, fontWeight = FontWeight.Normal,
        fontSize = 9.sp, letterSpacing = 0.03.em,
    )
}
