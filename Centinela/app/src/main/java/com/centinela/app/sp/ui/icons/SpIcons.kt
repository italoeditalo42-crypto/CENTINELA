package com.centinela.app.sp.ui.icons

import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.unit.dp

/*
 * Traducción 1:1 de js/icons.js a ImageVector. Mismo viewBox (24x24), mismo
 * stroke-width (1.7), stroke-linecap/linejoin "round", sin relleno salvo los
 * puntos marcados fill="currentColor" en el original (target, info, warning,
 * mask, wallet). El color real se aplica en el momento de dibujar via el
 * parámetro `tint` de Icon(), igual que `currentColor` en CSS.
 */

private fun circlePath(cx: Float, cy: Float, r: Float): String =
    "M${cx - r} $cy A$r $r 0 1 0 ${cx + r} $cy A$r $r 0 1 0 ${cx - r} $cy Z"

private fun linePath(x1: Float, y1: Float, x2: Float, y2: Float): String =
    "M$x1 $y1 L$x2 $y2"

private fun roundedRectPath(x: Float, y: Float, w: Float, h: Float, r: Float): String {
    val x2 = x + w; val y2 = y + h
    return "M${x + r} $y L${x2 - r} $y A$r $r 0 0 1 $x2 ${y + r} " +
        "L$x2 ${y2 - r} A$r $r 0 0 1 ${x2 - r} $y2 " +
        "L${x + r} $y2 A$r $r 0 0 1 $x ${y2 - r} " +
        "L$x ${y + r} A$r $r 0 0 1 ${x + r} $y Z"
}

private fun ImageVector.Builder.stroke(d: String) {
    path(
        fill = null,
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 1.7f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
        pathData = addPathNodes(d),
    )
}

private fun ImageVector.Builder.dot(d: String) {
    path(fill = SolidColor(Color.Black), pathData = addPathNodes(d))
}

private fun spIcon(name: String, build: ImageVector.Builder.() -> Unit): ImageVector =
    ImageVector.Builder(
        name = name, defaultWidth = 24.dp, defaultHeight = 24.dp,
        viewportWidth = 24f, viewportHeight = 24f,
    ).apply(build).build()

object SpIcons {
    val Home: ImageVector by lazy {
        spIcon("sp_home") {
            stroke("M3 11l9-7 9 7")
            stroke("M5 10v10h14V10")
            stroke("M10 20v-6h4v6")
        }
    }
    val Flag: ImageVector by lazy {
        spIcon("sp_flag") {
            stroke("M5 3v18")
            stroke("M5 4h11l-2.5 4L16 12H5")
        }
    }
    val User: ImageVector by lazy {
        spIcon("sp_user") {
            stroke(circlePath(12f, 8f, 4f))
            stroke("M4 21c1.5-5 6-6 8-6s6.5 1 8 6")
        }
    }
    val Shield: ImageVector by lazy {
        spIcon("sp_shield") {
            stroke("M12 3l7 3v6c0 4.5-3 7.5-7 9-4-1.5-7-4.5-7-9V6z")
            stroke("M9 12l2 2 4-4")
        }
    }
    val Send: ImageVector by lazy {
        spIcon("sp_send") { stroke("M4 12L20 4l-6 16-3-6-6-2z") }
    }
    val Target: ImageVector by lazy {
        spIcon("sp_target") {
            stroke(circlePath(12f, 12f, 8f))
            stroke(circlePath(12f, 12f, 4f))
            dot(circlePath(12f, 12f, 0.6f))
        }
    }
    val Pud: ImageVector by lazy {
        spIcon("sp_pud") {
            stroke(circlePath(6f, 6f, 2.4f))
            stroke(circlePath(18f, 6f, 2.4f))
            stroke(circlePath(12f, 18f, 2.4f))
            stroke("M8 7l3 9M16 7l-3 9M8.4 6h7.2")
        }
    }
    val ListIcon: ImageVector by lazy {
        spIcon("sp_list") {
            stroke(circlePath(4.5f, 6f, 1f))
            stroke(circlePath(4.5f, 12f, 1f))
            stroke(circlePath(4.5f, 18f, 1f))
            stroke(linePath(9f, 6f, 20f, 6f))
            stroke(linePath(9f, 12f, 20f, 12f))
            stroke(linePath(9f, 18f, 20f, 18f))
        }
    }
    val Zap: ImageVector by lazy {
        spIcon("sp_zap") { stroke("M13 2 4 14h7l-1 8 9-12h-7l1-8z") }
    }
    val Trend: ImageVector by lazy {
        spIcon("sp_trend") {
            stroke("M3 17l6-6 4 4 8-9")
            stroke("M15 6h6v6")
        }
    }
    val Book: ImageVector by lazy {
        spIcon("sp_book") {
            stroke("M4 5.5A2.5 2.5 0 0 1 6.5 3H20v16H6.5A2.5 2.5 0 0 0 4 21z")
            stroke("M4 19.5A2.5 2.5 0 0 1 6.5 17H20")
        }
    }
    val ChevDown: ImageVector by lazy {
        spIcon("sp_chev_down") { stroke("M6 9l6 6 6-6") }
    }
    val Check: ImageVector by lazy {
        spIcon("sp_check") { stroke("M4 12l5 5L20 6") }
    }
    val Info: ImageVector by lazy {
        spIcon("sp_info") {
            stroke(circlePath(12f, 12f, 9f))
            stroke(linePath(12f, 11f, 12f, 16f))
            dot(circlePath(12f, 8f, 0.6f))
        }
    }
    val Scale: ImageVector by lazy {
        spIcon("sp_scale") {
            stroke("M12 3v18")
            stroke("M5 21h14")
            stroke("M5 7l7-3 7 3")
            stroke("M2 12l3-6 3 6a3 3 0 0 1-6 0z")
            stroke("M16 12l3-6 3 6a3 3 0 0 1-6 0z")
        }
    }
    val Brain: ImageVector by lazy {
        spIcon("sp_brain") {
            stroke("M9 4a3 3 0 0 0-3 3 3 3 0 0 0-1 5.8A3.5 3.5 0 0 0 8.5 18 3 3 0 0 0 12 21a3 3 0 0 0 3.5-3 3.5 3.5 0 0 0 3.5-5.2A3 3 0 0 0 18 7a3 3 0 0 0-3-3 3 3 0 0 0-3 1 3 3 0 0 0-3-1z")
        }
    }
    val Eye: ImageVector by lazy {
        spIcon("sp_eye") {
            stroke("M2 12s3.5-7 10-7 10 7 10 7-3.5 7-10 7-10-7-10-7z")
            stroke(circlePath(12f, 12f, 3f))
        }
    }
    val Bolt: ImageVector by lazy {
        spIcon("sp_bolt") { stroke("M13 2 4 14h7l-1 8 9-12h-7l1-8z") }
    }
    val Refresh: ImageVector by lazy {
        spIcon("sp_refresh") {
            stroke("M4 12a8 8 0 0 1 13.7-5.7L21 9")
            stroke("M21 4v5h-5")
            stroke("M20 12a8 8 0 0 1-13.7 5.7L3 15")
            stroke("M3 20v-5h5")
        }
    }
    val ArrowRight: ImageVector by lazy {
        spIcon("sp_arrow_right") {
            stroke(linePath(4f, 12f, 20f, 12f))
            stroke("M14 6l6 6-6 6")
        }
    }
    val Heart: ImageVector by lazy {
        spIcon("sp_heart") { stroke("M12 20s-8-4.6-8-11a4.6 4.6 0 0 1 8-3 4.6 4.6 0 0 1 8 3c0 6.4-8 11-8 11z") }
    }
    val Wallet: ImageVector by lazy {
        spIcon("sp_wallet") {
            stroke(roundedRectPath(3f, 6f, 18f, 13f, 1.5f))
            stroke("M3 10h18")
            dot(circlePath(16.5f, 14f, 1f))
        }
    }
    val Users: ImageVector by lazy {
        spIcon("sp_users") {
            stroke(circlePath(8f, 8f, 3f))
            stroke(circlePath(17f, 9f, 2.5f))
            stroke("M2.5 20c.8-3.8 3.4-5.5 5.5-5.5s4.7 1.7 5.5 5.5")
            stroke("M14.5 14.7c1.7.3 3.5 1.7 4 5.3")
        }
    }
    val Mask: ImageVector by lazy {
        spIcon("sp_mask") {
            stroke(circlePath(12f, 12f, 9f))
            stroke("M12 3v18M3 12h18")
            dot(circlePath(8.5f, 10f, 1f))
            dot(circlePath(15.5f, 14f, 1f))
        }
    }
    val Moon: ImageVector by lazy {
        spIcon("sp_moon") { stroke("M20 14.5A8.5 8.5 0 1 1 9.5 4a7 7 0 0 0 10.5 10.5z") }
    }
    val Warning: ImageVector by lazy {
        spIcon("sp_warning") {
            stroke("M12 3l10 18H2z")
            stroke(linePath(12f, 9f, 12f, 14f))
            dot(circlePath(12f, 17f, 0.6f))
        }
    }
    val X: ImageVector by lazy {
        spIcon("sp_x") {
            stroke(linePath(5f, 5f, 19f, 19f))
            stroke(linePath(19f, 5f, 5f, 19f))
        }
    }
    val Compass: ImageVector by lazy {
        spIcon("sp_compass") {
            stroke(circlePath(12f, 12f, 9f))
            stroke("M15 9l-2 6-6 2 2-6z")
        }
    }
}
