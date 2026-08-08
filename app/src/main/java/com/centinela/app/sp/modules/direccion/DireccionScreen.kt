package com.centinela.app.sp.modules.direccion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.modules.common.DashList
import com.centinela.app.sp.modules.common.SegmentBar
import com.centinela.app.sp.ui.components.AngularPanel
import com.centinela.app.sp.ui.components.ModuleBanner
import com.centinela.app.sp.ui.components.PanelTitle
import com.centinela.app.sp.ui.components.SpIcon
import com.centinela.app.sp.ui.components.SpPill
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpInk0
import com.centinela.app.sp.ui.theme.SpInk1
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

private data class Eje(val num: String, val icon: ImageVector, val title: String, val body: String, val rumbo: String, val pct: Int)

private val EJES = listOf(
    Eje("01", SpIcons.Heart, "SALUD", "Mantener un cuerpo capaz de sostener la vida que quiero construir.", "La salud no es un premio. Es infraestructura.", 86),
    Eje("02", SpIcons.Shield, "CARÁCTER", "Desarrollar una personalidad capaz de actuar conforme a principios incluso cuando resulte incómodo.", "El carácter reduce la distancia entre intención y acción.", 78),
    Eje("03", SpIcons.Brain, "CONOCIMIENTO", "Aprender continuamente para comprender mejor la realidad.", "No acumular información. Reducir errores de interpretación.", 74),
    Eje("04", SpIcons.Trend, "CAPACIDAD", "Convertir conocimiento en habilidades útiles. Incrementar la autonomía mediante competencia.", "Cada habilidad adquirida amplía las posibilidades futuras.", 70),
    Eje("05", SpIcons.Target, "PROPÓSITO", "Construir deliberadamente una vida propia en lugar de aceptar pasivamente una vida por defecto.", "Cada decisión debe acercarme a la vida que elegí construir.", 72),
    Eje("06", SpIcons.Users, "VÍNCULOS", "Construir relaciones compatibles con la vida que deseo.", "Alejarme progresivamente de vínculos que destruyan mi dirección.", 68),
    Eje("07", SpIcons.Moon, "BIENESTAR MENTAL", "Aprender a regular pensamientos, emociones e impulsos sin depender constantemente de la evasión.", "La estabilidad interior permite sostener el rumbo cuando las circunstancias cambian.", 64),
)

@Composable
fun DireccionScreen() {
    val accent = spAccent()
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            ModuleBanner(
                title = "DIRECCIÓN", roman = "IV",
                glyph = { SpIcon(icon = SpIcons.Compass, tint = accent.base, size = 46.dp) },
            )
        }
        item {
            AngularPanel {
                Text(
                    "Tener una dirección fija sirve para impedir que la acción se convierta en movimiento sin sentido.",
                    style = SpType.bodyLarge, color = SpInk0,
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "No necesito saber exactamente cómo será mi futuro, pero sí tener claro el tipo de vida que estoy " +
                        "construyendo y el rumbo al que todas mis decisiones deben acercarme.",
                    style = SpType.body, color = SpInk1,
                )
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    SpPill("RUMBO: FIJO")
                    SpPill("DISCIPLINA: ACTIVA")
                    SpPill("ENFOQUE: TOTAL")
                }
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("Principio de dirección", leading = { SpIcon(SpIcons.Shield, tint = accent.base) })
                    DashList(listOf(
                        "No tomo decisiones únicamente porque sean posibles, cómodas o atractivas.",
                        "Las tomo considerando si fortalecen o debilitan la vida que he decidido construir.",
                        "Mi dirección no depende de emociones momentáneas.",
                        "Es el criterio que mantiene alineados mis principios, mi identidad y mis acciones a lo largo del tiempo.",
                    ))
                }
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("Definición de éxito", leading = { SpIcon(SpIcons.Target, tint = accent.base) })
                    Text("El éxito no consiste únicamente en obtener resultados externos. Consiste en que exista una creciente coherencia entre:", style = SpType.body, color = SpInk1)
                    Spacer(Modifier.height(8.dp))
                    DashList(listOf("Lo que considero correcto.", "Lo que decido.", "Lo que hago.", "La persona en la que me convierto."))
                    Spacer(Modifier.height(8.dp))
                    Text("Los resultados importan. Pero la congruencia sostiene esos resultados.", style = SpType.body, color = SpInk1, fontWeight = FontWeight.SemiBold)
                }
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("Visión de largo plazo", leading = { SpIcon(SpIcons.Eye, tint = accent.base) })
                    Text("Aspiro a construir una vida donde:", style = SpType.body, color = SpInk1)
                    Spacer(Modifier.height(8.dp))
                    DashList(listOf(
                        "mi cuerpo me permita actuar;", "mi carácter inspire confianza;",
                        "mis conocimientos aumenten mi libertad;", "mis capacidades produzcan valor;",
                        "mis relaciones sean compatibles con mis principios;",
                        "y mi estabilidad interior no dependa constantemente de las circunstancias.",
                    ))
                }
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SpIcon(SpIcons.Compass, tint = accent.base, size = 15.dp)
                    Spacer(Modifier.width(8.dp))
                    Text("LOS SIETE EJES DEL RUMBO", style = SpType.panelTitle, color = accent.base)
                }
                Text("Cada uno representa una dirección de crecimiento permanente.", style = SpType.monoSm, color = SpInk2)
            }
        }
        item { EjesGrid(EJES) }
        item {
            AngularPanel {
                PanelTitle("Declaración final")
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(18.dp)) {
                    SpIcon(SpIcons.Compass, tint = accent.base, size = 56.dp)
                    Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        DeclItem(SpIcons.Shield, "No controlo el futuro, ni las circunstancias, ni los resultados.", Modifier.weight(1f))
                        DeclItem(SpIcons.Compass, "Pero sí puedo controlar el rumbo desde el que tomo mis decisiones.", Modifier.weight(1f))
                        DeclItem(SpIcons.Bolt, "Mientras conserve ese rumbo, incluso los errores forman parte del avance.", Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun EjesGrid(ejes: List<Eje>) {
    val accent = spAccent()
    val rows = ejes.chunked(4)
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
                row.forEach { eje ->
                    AngularPanel(modifier = Modifier.weight(1f)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(eje.num, style = SpType.monoLabel, color = SpInk2)
                            SpIcon(eje.icon, tint = accent.base, size = 20.dp)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(eje.title, style = SpType.label, color = SpInk0)
                        Spacer(Modifier.height(6.dp))
                        Text(eje.body, style = SpType.body.copy(fontSize = SpType.mono.fontSize), color = SpInk1)
                        Spacer(Modifier.height(6.dp))
                        Text(eje.rumbo, style = SpType.body.copy(fontSize = SpType.mono.fontSize), color = accent.base, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(10.dp))
                        Text("RUMBO", style = SpType.monoSm, color = SpInk2)
                        Spacer(Modifier.height(4.dp))
                        SegmentBar(pct = eje.pct)
                    }
                }
                repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun DeclItem(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    val accent = spAccent()
    Column(modifier = modifier) {
        SpIcon(icon = icon, tint = accent.base, size = 20.dp)
        Spacer(Modifier.height(8.dp))
        Text(text, style = SpType.body, color = SpInk1)
    }
}
