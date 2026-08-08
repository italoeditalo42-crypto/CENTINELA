package com.centinela.app.sp.modules.identidad

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.modules.common.CheckList
import com.centinela.app.sp.ui.charts.RadarChart
import com.centinela.app.sp.ui.components.AngularPanel
import com.centinela.app.sp.ui.components.ModuleBanner
import com.centinela.app.sp.ui.components.PanelTitle
import com.centinela.app.sp.ui.components.SpIcon
import com.centinela.app.sp.ui.components.SpPill
import com.centinela.app.sp.ui.components.SpPillStyle
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpInk0
import com.centinela.app.sp.ui.theme.SpInk1
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

private val CLASE_PERSONA = listOf(
    "Cumple su palabra.", "Actúa con honestidad.", "Asume la responsabilidad de su vida.",
    "Piensa antes de actuar.", "Aprende de forma continua.", "Cuida su salud física y mental.",
    "Mantiene el orden en su entorno.", "Respeta su cuerpo.",
    "Busca comprender la realidad antes que confirmar sus deseos.",
    "Acepta la evidencia aunque contradiga sus creencias.",
    "Corrige sus errores sin justificarlos ni postergarlos.",
    "Hace lo correcto incluso cuando nadie observa.", "Vive con disciplina.",
)

private val PRINCIPIOS = listOf(
    "La verdad está por encima de la comodidad.", "La evidencia está por encima del ego.",
    "Soy responsable de mis decisiones.", "No utilizo excusas para justificar mi conducta.",
    "Cumplo mis compromisos o los renuncio con honestidad.", "Me respeto a mí mismo y a los demás.",
    "No sacrifico el largo plazo por una satisfacción inmediata.", "La disciplina prevalece sobre el impulso.",
    "Corregir un error es mejor que defenderlo.", "Mi carácter vale más que cualquier resultado.",
)

private val ESTANDAR_MINIMO = listOf(
    "Mantener una buena higiene personal.", "Alimentar mi cuerpo de forma saludable.",
    "Dormir lo necesario para recuperarme.", "Mantener mi espacio limpio y ordenado.",
    "Mover mi cuerpo y desarrollar fuerza física.", "Aprender de forma constante.",
    "Buscar información que cuestione mis propias ideas.", "Corregir mis errores tan pronto como los detecto.",
    "Hablar con honestidad.", "Cumplir lo que prometo.", "Resolver los problemas en lugar de evitarlos.",
    "Cuidar mis relaciones con respeto.",
)

private val CAP_LABELS = listOf("Disciplina", "Valentía", "Perseverancia", "Adaptabilidad", "Curiosidad", "Coherencia", "Prudencia", "Serenidad")
private val CAP_VALUES = listOf(82f, 68f, 74f, 70f, 80f, 77f, 72f, 65f)

private val CICLO = listOf(
    SpIcons.Eye to "Observo la realidad",
    SpIcons.Book to "Consulto mis principios",
    SpIcons.Brain to "Tomo decisiones conscientes",
    SpIcons.Bolt to "Actúo con disciplina",
    SpIcons.Refresh to "Evalúo y corrijo",
    SpIcons.Check to "Me fortalezco y continúo",
)

@Composable
fun IdentidadScreen() {
    val accent = spAccent()
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            ModuleBanner(
                title = "IDENTIDAD", roman = "II", sub = "QUIÉN SOY Y CÓMO ACTÚO",
                glyph = { SpIcon(icon = SpIcons.User, tint = accent.base, size = 46.dp) },
            )
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("NÚCLEO DEL OPERADOR", style = SpType.monoLabel, color = SpInk2)
                SpPill("ESTABLE", style = SpPillStyle.Accent)
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("Mi esencia", leading = { SpIcon(SpIcons.User, tint = accent.base) })
                    SpIcon(icon = SpIcons.User, tint = accent.base.copy(alpha = 0.55f), size = 64.dp, modifier = Modifier.padding(bottom = 12.dp))
                    Text(
                        "Mi identidad no depende de mis emociones, impulsos, resultados recientes ni errores. " +
                            "Está determinada por los principios que elijo sostener y por la manera en que actúo. " +
                            "Soy el único responsable de mis decisiones. Mi conducta no se negocia con el estado de " +
                            "ánimo ni con las circunstancias. Respondo a los principios que he decidido adoptar. " +
                            "No busco la perfección. Busco coherencia. Cuando me equivoco, reconozco el error, lo corrijo y continúo.",
                        style = SpType.body, color = SpInk1,
                    )
                }
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("¿Qué clase de persona elijo ser?", leading = { SpIcon(SpIcons.Info, tint = accent.base) })
                    CheckList(CLASE_PERSONA)
                }
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("¿Qué principios gobiernan mi conducta?", leading = { SpIcon(SpIcons.Scale, tint = accent.base) })
                    CheckList(PRINCIPIOS)
                }
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("¿Qué capacidades desarrollo?", leading = { SpIcon(SpIcons.Brain, tint = accent.base) })
                    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                        RadarChart(labels = CAP_LABELS, values = CAP_VALUES, max = 100f, color = accent.base)
                    }
                    Spacer(Modifier.height(6.dp))
                    Text(
                        "No considero estos rasgos cualidades innatas. Son capacidades que se fortalecen mediante la práctica constante.",
                        style = SpType.body.copy(fontSize = SpType.mono.fontSize), color = SpInk2,
                    )
                }
            }
        }
        item {
            AngularPanel(glow = true) {
                PanelTitle("¿Qué considero el estándar mínimo?", leading = { SpIcon(SpIcons.Shield, tint = accent.base) })
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    CheckList(ESTANDAR_MINIMO.filterIndexed { i, _ -> i % 2 == 0 }, modifier = Modifier.weight(1f))
                    CheckList(ESTANDAR_MINIMO.filterIndexed { i, _ -> i % 2 == 1 }, modifier = Modifier.weight(1f))
                }
                Spacer(Modifier.height(12.dp))
                CalloutBox("Estos no son logros extraordinarios. Son el estándar mínimo de la persona que he decidido ser.")
            }
        }
        item {
            AngularPanel {
                PanelTitle("¿Cómo opero?", leading = { SpIcon(SpIcons.Refresh, tint = accent.base) })
                androidx.compose.foundation.layout.FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    CICLO.forEachIndexed { i, (ic, label) ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(84.dp)) {
                            SpIcon(icon = ic, tint = accent.base, size = 22.dp)
                            Spacer(Modifier.height(6.dp))
                            Text(label, style = SpType.monoSm, color = SpInk1, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                        }
                        if (i < CICLO.size - 1) {
                            SpIcon(icon = SpIcons.ArrowRight, tint = SpInk2, size = 16.dp, modifier = Modifier.padding(top = 12.dp))
                        }
                    }
                }
            }
        }
        item {
            AngularPanel {
                PanelTitle("Declaración final")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    DeclItem(SpIcons.Trend, "Construyo mi vida mediante decisiones coherentes con mis principios.", Modifier.weight(1f))
                    DeclItem(SpIcons.Bolt, "No confundo planificar con progresar ni las buenas intenciones con la acción.", Modifier.weight(1f))
                    DeclItem(SpIcons.User, "Mi identidad no se descubre; se demuestra. Cada decisión fortalece o debilita el carácter que estoy construyendo.", Modifier.weight(1f))
                }
                Spacer(Modifier.height(14.dp))
                Text(
                    "POR ELLO, PROCURO VIVIR DE UNA FORMA QUE HAGA EVIDENTE, PRIMERO PARA MÍ MISMO, QUIÉN HE DECIDIDO SER.",
                    style = SpType.label, color = SpInk0, textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
private fun DeclItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, modifier: Modifier = Modifier) {
    val accent = spAccent()
    Column(modifier = modifier) {
        SpIcon(icon = icon, tint = accent.base, size = 20.dp)
        Spacer(Modifier.height(8.dp))
        Text(text, style = SpType.body, color = SpInk1)
    }
}

@Composable
private fun CalloutBox(text: String) {
    val accent = spAccent()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(accent.base.copy(alpha = 0.08f))
            .padding(14.dp),
    ) {
        Text(text, style = SpType.body, color = SpInk0, fontWeight = FontWeight.Medium)
    }
}
