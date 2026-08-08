package com.centinela.app.sp.ui.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * Corte hexagonal en dos esquinas (clip-path: polygon(N 0,100% 0,100% calc(100% - N),calc(100% - N) 100%,0 100%,0 N))
 * — usado en .panel, .quick-btn, .radio-opt span, .acc, .module-banner .roman
 */
class CornerCutShape(private val cut: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val c = minOf(cut, size.minDimension / 2f)
        val path = Path().apply {
            moveTo(c, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height - c)
            lineTo(size.width - c, size.height)
            lineTo(0f, size.height)
            lineTo(0f, c)
            close()
        }
        return Outline.Generic(path)
    }
}

/**
 * Corte en las 4 esquinas (clip-path octagonal completo)
 * — usado en .navlink
 */
class OctagonCutShape(private val cut: Float) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val c = minOf(cut, size.minDimension / 2f)
        val w = size.width
        val h = size.height
        val path = Path().apply {
            moveTo(c, 0f)
            lineTo(w - c, 0f)
            lineTo(w, c)
            lineTo(w, h - c)
            lineTo(w - c, h)
            lineTo(c, h)
            lineTo(0f, h - c)
            lineTo(0f, c)
            close()
        }
        return Outline.Generic(path)
    }
}

object SpCuts {
    const val sm = 6f
    const val md = 8f
    const val lg = 10f
    const val xl = 12f
}
