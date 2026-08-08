package com.centinela.app.sp.modules.antiidentidad

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
import com.centinela.app.sp.modules.common.ChipRow
import com.centinela.app.sp.modules.common.StepsList
import com.centinela.app.sp.modules.common.XList
import com.centinela.app.sp.ui.components.AngularPanel
import com.centinela.app.sp.ui.components.ModuleBanner
import com.centinela.app.sp.ui.components.PanelTitle
import com.centinela.app.sp.ui.components.SpIcon
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpInk0
import com.centinela.app.sp.ui.theme.SpInk1
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

private data class Area(val icon: ImageVector, val num: String, val title: String, val items: List<String>)

private val AREAS = listOf(
    Area(SpIcons.Heart, "01", "CUERPO Y ENERGÍA", listOf(
        "Sedentarismo prolongado.", "Descuidar la actividad física.", "Alimentarme por comodidad.",
        "Sacrificar el sueño por distracciones.", "Vivir agotado sin investigar causas.",
        "Excederme con azúcar o estímulos.", "Usar pantallas cerca de dormir.")),
    Area(SpIcons.Brain, "02", "MENTE, ATENCIÓN E INFORMACIÓN", listOf(
        "Contenido corto compulsivo.", "Estímulos fáciles para evitar emociones.", "Entretenimiento que desplaza lo importante.",
        "Dejar que el algoritmo decida.", "Adoptar ideas sin cuestionar.", "Repetir opiniones ajenas.",
        "Defender creencias por identidad.", "Confundir información con comprensión.", "Usar plataformas sin límite consciente.")),
    Area(SpIcons.Flag, "03", "ACTITUD ANTE LA VIDA", listOf(
        "Victimizarme y culpar factores externos.", "Buscar excusas en lugar de soluciones.", "Compararme para sentirme superior.",
        "Ignorar lo que tengo por lo que falta.", "Usar la gratitud como sustituto de la mejora.")),
    Area(SpIcons.Wallet, "04", "RECURSOS", listOf(
        "Consumir recursos sin considerar el futuro.", "Gastar impulsivamente.", "Dejar de desarrollar capacidades útiles.",
        "Usar recursos sin dirección clara.")),
    Area(SpIcons.Users, "05", "RELACIONES Y ENTORNO", listOf(
        "Rodearme de estancamiento.", "Permitir que otros definan mi tiempo o dirección.", "Aislarme de relaciones saludables.",
        "Mantener relaciones perjudiciales por miedo o comodidad.")),
    Area(SpIcons.Target, "06", "ACCIÓN Y DISCIPLINA", listOf(
        "Posponer el inicio constantemente.", "Actuar solo con motivación.", "Abandonar ante la primera dificultad.",
        "Sustituir acción por sobreanálisis.", "Usar perfeccionismo para evitar el error.")),
    Area(SpIcons.Mask, "07", "MIEDO Y EGO", listOf(
        "Evitar situaciones incómodas por miedo al juicio.", "No intentar nada para evitar fracasar.",
        "Defender el orgullo antes que mejorar.", "Confundir evitar el fracaso con éxito.")),
    Area(SpIcons.Moon, "08", "VIDA INTERIOR", listOf(
        "Vivir en automático.", "No revisar si mis acciones tienen sentido.", "Aferrarme al pasado.",
        "Usar consumo o distracción para evitar emociones.", "Juzgarme con dureza extrema en lugar de observar patrones.")),
)

private val CONVIERTE = listOf(
    "La procrastinación" to "\"espera razonable\"", "El miedo" to "\"prudencia\"", "El ego" to "\"convicción\"",
    "La distracción" to "\"descanso\"", "La costumbre" to "\"identidad\"", "La intención" to "sustituto de la acción",
)

private val SENALES = listOf(
    "Justificar lo que antes corregía.", "Defender errores evidentes.", "Actuar por impulso.",
    "Abandonar compromisos.", "Posponer decisiones incómodas.", "Evitar la evidencia.",
    "Creer que mañana será distinto sin cambiar hoy.",
)

private val HERRAMIENTAS = listOf(
    "Racionalización", "Autoengaño", "Victimismo", "Perfeccionismo", "Orgullo",
    "Impulsividad", "Comodidad", "Distracción", "Gratificación inmediata", "Comparación constante",
)

private val PASOS = listOf(
    "La nombro.", "La acepto sin justificarla.", "Identifico el principio vulnerado.",
    "Corrijo la acción.", "Continúo.",
)

@Composable
fun AntiidentidadScreen() {
    val accent = spAccent()
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            ModuleBanner(
                title = "ANTIIDENTIDAD", roman = "III", sub = "PATRONES A DETECTAR, CORREGIR Y EVITAR",
                glyph = { SpIcon(icon = SpIcons.Mask, tint = accent.base, size = 46.dp) },
            )
        }
        item {
            AngularPanel {
                Text(
                    "La antiidentidad no define quién soy. Señala los patrones que debilitan mi carácter, " +
                        "erosionan mi conducta y me alejan de los principios que he elegido sostener.",
                    style = SpType.body, color = SpInk1,
                )
                Spacer(Modifier.height(14.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    TipItem(SpIcons.Warning, "No aparece de golpe. Se instala poco a poco, cada vez que justifico lo innecesario, ignoro lo evidente o tolero una desviación que ya sé que debo corregir.", Modifier.weight(1f))
                    TipItem(SpIcons.Info, "No debo odiarla ni dramatizarla. Debo reconocerla con precisión para recuperar el control.", Modifier.weight(1f))
                    TipItem(SpIcons.Refresh, "Reconocer es poder. Corregir es fortalecer. Continuar es dominar.", Modifier.weight(1f))
                }
            }
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("¿Qué es la antiidentidad?", leading = { SpIcon(SpIcons.Info, tint = accent.base) })
                    Text("Es el conjunto de hábitos, sesgos, excusas y automatismos que me empujan a actuar contra mis principios.", style = SpType.body, color = SpInk1)
                }
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("¿Cómo opera?", leading = { SpIcon(SpIcons.Refresh, tint = accent.base) })
                    Text("La antiidentidad no suele presentarse como un error grave. Opera por desgaste.", style = SpType.body, color = SpInk1)
                }
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("Convierte", leading = { SpIcon(SpIcons.ArrowRight, tint = accent.base) })
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        CONVIERTE.forEach { (a, b) ->
                            Row(verticalAlignment = Alignment.Top) {
                                SpIcon(SpIcons.ArrowRight, tint = accent.base, size = 13.dp, modifier = Modifier.padding(top = 4.dp))
                                Spacer(Modifier.width(8.dp))
                                Text("$a en $b", style = SpType.body, color = SpInk1)
                            }
                        }
                    }
                }
            }
        }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SpIcon(SpIcons.Eye, tint = accent.base, size = 15.dp)
                Spacer(Modifier.width(8.dp))
                Text("ÁREAS A DETECTAR", style = SpType.panelTitle, color = accent.base)
            }
        }
        item {
            AreasGrid(AREAS)
        }
        item {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("Señales de que está tomando control", leading = { SpIcon(SpIcons.Warning, tint = accent.base) })
                    Text("Empiezo a:", style = SpType.monoLabel, color = SpInk2, modifier = Modifier.padding(bottom = 8.dp))
                    XList(SENALES)
                }
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("Herramientas que utiliza", leading = { SpIcon(SpIcons.Bolt, tint = accent.base) })
                    Text("La antiidentidad suele apoyarse en:", style = SpType.monoLabel, color = SpInk2, modifier = Modifier.padding(bottom = 8.dp))
                    ChipRow(HERRAMIENTAS)
                }
                AngularPanel(modifier = Modifier.weight(1f)) {
                    PanelTitle("Cómo recupero el control", leading = { SpIcon(SpIcons.Shield, tint = accent.base) })
                    Text("Cuando detecto una desviación:", style = SpType.monoLabel, color = SpInk2, modifier = Modifier.padding(bottom = 8.dp))
                    StepsList(PASOS)
                    Spacer(Modifier.height(8.dp))
                    Text("No necesito sentirme distinto. Necesito volver a actuar conforme a mis principios.", style = SpType.body.copy(fontSize = SpType.mono.fontSize), color = SpInk2)
                }
            }
        }
        item {
            AngularPanel {
                PanelTitle("Declaración final")
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    DeclItem(SpIcons.Refresh, "La antiidentidad siempre existirá como posibilidad.", Modifier.weight(1f))
                    DeclItem(SpIcons.Shield, "No intento eliminarla. Intento impedir que gobierne mis decisiones.", Modifier.weight(1f))
                    DeclItem(SpIcons.Trend, "Cada vez que elijo un principio por encima del impulso, debilito la antiidentidad y fortalezco mi carácter.", Modifier.weight(1f))
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.Top,
            ) {
                SpIcon(SpIcons.Warning, tint = accent.base, size = 18.dp)
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("La aparición de un patrón incompatible no define quién soy.", style = SpType.body, color = SpInk1)
                    Text(
                        "La incapacidad o falta de voluntad para corregirlo repetidamente sí constituye evidencia de un problema que debe ser abordado.",
                        style = SpType.body, color = SpInk0, fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }
    }
}

@Composable
private fun AreasGrid(areas: List<Area>) {
    val accent = spAccent()
    val rows = areas.chunked(4)
    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        rows.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp), modifier = Modifier.fillMaxWidth()) {
                row.forEach { area ->
                    AngularPanel(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.Top) {
                            SpIcon(area.icon, tint = accent.base, size = 20.dp)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(area.num, style = SpType.monoLabel, color = SpInk2)
                                Text(area.title, style = SpType.label, color = SpInk0)
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            area.items.forEach { item ->
                                Text("— $item", style = SpType.body.copy(fontSize = SpType.mono.fontSize), color = SpInk1)
                            }
                        }
                    }
                }
                repeat(4 - row.size) { Spacer(Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun TipItem(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    val accent = spAccent()
    Column(modifier = modifier) {
        SpIcon(icon, tint = accent.base, size = 18.dp)
        Spacer(Modifier.height(8.dp))
        Text(text, style = SpType.body.copy(fontSize = SpType.mono.fontSize), color = SpInk1)
    }
}

@Composable
private fun DeclItem(icon: ImageVector, text: String, modifier: Modifier = Modifier) {
    val accent = spAccent()
    Column(modifier = modifier) {
        SpIcon(icon, tint = accent.base, size = 20.dp)
        Spacer(Modifier.height(8.dp))
        Text(text, style = SpType.body, color = SpInk1)
    }
}
