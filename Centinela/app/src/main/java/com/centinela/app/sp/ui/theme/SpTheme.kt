package com.centinela.app.sp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

/** Acento activo del módulo actual — equivalente a [data-theme] en tokens.css */
val LocalSpAccent = compositionLocalOf { SpAmber }

@Composable
fun SpModuleTheme(theme: SpModuleTheme, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalSpAccent provides theme.accent) {
        content()
    }
}

@Composable
fun spAccent(): SpAccent = LocalSpAccent.current

/** Fondo base de la app: degradado oscuro casi negro, igual a --bg-0/--bg-1 */
@Composable
fun SpBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(SpBg1, SpBg0, SpVoid)))
    ) {
        content()
    }
}
