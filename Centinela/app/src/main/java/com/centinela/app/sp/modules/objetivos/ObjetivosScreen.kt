package com.centinela.app.sp.modules.objetivos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.centinela.app.sp.data.objetivos.AreaEntity
import com.centinela.app.sp.data.objetivos.AreaWithPuntos
import com.centinela.app.sp.data.objetivos.IND_FISICOS
import com.centinela.app.sp.data.objetivos.IND_RENDIMIENTO
import com.centinela.app.sp.data.objetivos.IndicadorEntity
import com.centinela.app.sp.data.objetivos.ObjetivosViewModel
import com.centinela.app.sp.data.objetivos.PuntoEntity
import com.centinela.app.sp.ui.charts.RadarChart
import com.centinela.app.sp.ui.charts.TargetChart
import com.centinela.app.sp.ui.components.AngularPanel
import com.centinela.app.sp.ui.components.GlowButton
import com.centinela.app.sp.ui.components.ModuleBanner
import com.centinela.app.sp.ui.components.PanelTitle
import com.centinela.app.sp.ui.components.SpButtonStyle
import com.centinela.app.sp.ui.components.SpConfirmDialog
import com.centinela.app.sp.ui.components.SpIcon
import com.centinela.app.sp.ui.components.SpPromptDialog
import com.centinela.app.sp.ui.components.SpTextField
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpInk0
import com.centinela.app.sp.ui.theme.SpInk1
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

private val DIAS = listOf("D", "L", "M", "X", "J", "V", "S")
private val PUNTO_ICONS = listOf(SpIcons.Heart, SpIcons.Zap, SpIcons.Heart, SpIcons.Compass, SpIcons.Refresh, SpIcons.Trend)

@Composable
fun ObjetivosScreen() {
    val vm: ObjetivosViewModel = viewModel()
    val ui by vm.ui.collectAsState()
    val accent = spAccent()

    var openAreas by remember { mutableStateOf(setOf<String>()) }
    var openPuntos by remember { mutableStateOf(setOf<String>()) }
    var showAddArea by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            ModuleBanner(
                title = "SISTEMA DE OBJETIVOS", roman = "V",
                glyph = { SpIcon(icon = SpIcons.Target, tint = accent.base, size = 46.dp) },
            )
        }
        item {
            AngularPanel {
                Text(
                    "Áreas de vida con objetivos macro y micro, completamente editables. Crea tantas áreas, puntos y " +
                        "objetivos como necesites; todo se guarda en este dispositivo.",
                    style = SpType.body, color = SpInk1,
                )
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    GlowButton(
                        text = "Agregar área", onClick = { showAddArea = true }, style = SpButtonStyle.Solid,
                        leading = { SpIcon(SpIcons.Zap, tint = Color(0xFF050708), size = 14.dp) },
                    )
                    GlowButton(text = "Expandir todo", onClick = { openAreas = ui.areas.map { it.area.id }.toSet() })
                    GlowButton(text = "Colapsar todo", onClick = { openAreas = emptySet(); openPuntos = emptySet() })
                }
            }
        }
        items(ui.areas, key = { it.area.id }) { aw ->
            AreaAccordion(
                aw = aw,
                open = aw.area.id in openAreas,
                onToggle = { openAreas = if (aw.area.id in openAreas) openAreas - aw.area.id else openAreas + aw.area.id },
                openPuntos = openPuntos,
                onTogglePunto = { id -> openPuntos = if (id in openPuntos) openPuntos - id else openPuntos + id },
                onAddPunto = { titulo -> vm.addPunto(aw.area.id, titulo); openAreas = openAreas + aw.area.id },
                onDeleteArea = { vm.deleteArea(aw.area) },
                onUpdatePunto = { vm.updatePunto(it) },
                onDeletePunto = { vm.deletePunto(it) },
            )
        }
        item {
            AngularPanel {
                PanelTitle("Indicadores físicos", leading = { SpIcon(SpIcons.Trend, tint = accent.base) })
                Text("Indicadores antropométricos", style = SpType.monoSm, color = SpInk2, modifier = Modifier.padding(bottom = 10.dp))
                IndicadorTable(rows = IND_FISICOS, key = "fisicos", data = ui.fisicos, finalLabel = "Realizado final", onCommit = vm::setIndicador)
            }
        }
        item {
            AngularPanel {
                PanelTitle("Métricas de rendimiento", leading = { SpIcon(SpIcons.Zap, tint = accent.base) })
                IndicadorTable(rows = IND_RENDIMIENTO, key = "rendimiento", data = ui.rendimiento, finalLabel = "Resultado final", onCommit = vm::setIndicador)
            }
        }
    }

    if (showAddArea) {
        SpPromptDialog(
            title = "Nueva área", placeholder = "Ej: Trabajo y Finanzas",
            onDismiss = { showAddArea = false },
            onConfirm = { vm.addArea(it); showAddArea = false },
        )
    }
}

@Composable
private fun AreaAccordion(
    aw: AreaWithPuntos,
    open: Boolean,
    onToggle: () -> Unit,
    openPuntos: Set<String>,
    onTogglePunto: (String) -> Unit,
    onAddPunto: (String) -> Unit,
    onDeleteArea: () -> Unit,
    onUpdatePunto: (PuntoEntity) -> Unit,
    onDeletePunto: (PuntoEntity) -> Unit,
) {
    val accent = spAccent()
    var showAddPunto by remember { mutableStateOf(false) }
    var showDelete by remember { mutableStateOf(false) }
    val rotation by androidx.compose.animation.core.animateFloatAsState(if (open) 180f else 0f, label = "chev")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .background(Color(0xB8040E14))
            .border(1.dp, accent.base.copy(alpha = if (open) 0.6f else 0.32f), RoundedCornerShape(2.dp)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onToggle).padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Tag("ÁREA", accent.base)
            Spacer(Modifier.width(12.dp))
            Text(aw.area.nombre, style = SpType.label, color = SpInk0, modifier = Modifier.weight(1f))
            Text("${aw.puntos.size} punto(s)", style = SpType.monoSm, color = SpInk2)
            Spacer(Modifier.width(10.dp))
            SpIcon(SpIcons.ChevDown, tint = accent.base, size = 16.dp, modifier = Modifier.rotate(rotation))
        }
        AnimatedVisibility(open, enter = fadeIn() + expandVertically(), exit = fadeOut() + shrinkVertically()) {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                aw.puntos.forEachIndexed { i, pt ->
                    PuntoAccordion(
                        area = aw.area, punto = pt, index = i,
                        open = pt.id in openPuntos, onToggle = { onTogglePunto(pt.id) },
                        onUpdate = onUpdatePunto, onDelete = { onDeletePunto(pt) },
                    )
                }
                GlowButton(
                    text = "Agregar objetivo macro", onClick = { showAddPunto = true }, style = SpButtonStyle.Solid,
                    modifier = Modifier.fillMaxWidth(),
                )
                GlowButton(
                    text = "Eliminar área", onClick = { showDelete = true }, style = SpButtonStyle.Danger,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

    if (showAddPunto) {
        SpPromptDialog(
            title = "Título del objetivo macro", placeholder = "Ej: Optimizar mi alimentación",
            onDismiss = { showAddPunto = false },
            onConfirm = { onAddPunto(it); showAddPunto = false },
        )
    }
    if (showDelete) {
        SpConfirmDialog(
            message = "¿Eliminar el área \"${aw.area.nombre}\" y todos sus objetivos?",
            onDismiss = { showDelete = false },
            onConfirm = { onDeleteArea(); showDelete = false },
        )
    }
}

@Composable
private fun PuntoAccordion(
    area: AreaEntity,
    punto: PuntoEntity,
    index: Int,
    open: Boolean,
    onToggle: () -> Unit,
    onUpdate: (PuntoEntity) -> Unit,
    onDelete: () -> Unit,
) {
    val accent = spAccent()
    var showDelete by remember { mutableStateOf(false) }
    var showAddMicro by remember { mutableStateOf(false) }
    val rotation by androidx.compose.animation.core.animateFloatAsState(if (open) 180f else 0f, label = "chev2")
    val icoName = PUNTO_ICONS[index % PUNTO_ICONS.size]

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(2.dp))
            .background(Color(0x99050B10))
            .border(1.dp, accent.base.copy(alpha = if (open) 0.5f else 0.25f), RoundedCornerShape(2.dp)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onToggle).padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Tag("PUNTO ${index + 1}", accent.base)
            Spacer(Modifier.width(10.dp))
            Text(punto.titulo.uppercase(), style = SpType.label.copy(fontSize = SpType.body.fontSize), color = SpInk0, modifier = Modifier.weight(1f))
            SpIcon(icoName, tint = accent.base, size = 16.dp)
            Spacer(Modifier.width(10.dp))
            SpIcon(SpIcons.ChevDown, tint = accent.base, size = 15.dp, modifier = Modifier.rotate(rotation))
        }
        AnimatedVisibility(open, enter = fadeIn() + expandVertically(), exit = fadeOut() + shrinkVertically()) {
            Column(modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 14.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                FieldLabel("Objetivo macro", SpIcons.Target)
                SpTextField(itemKey = "${punto.id}:titulo", initialValue = punto.titulo, onCommit = { onUpdate(punto.copy(titulo = it)) })

                WhyAccordion(punto, onUpdate)

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MiniField("Conducta general", SpIcons.Refresh, punto, "conducta", punto.conducta, Modifier.weight(1f), onUpdate)
                    MiniField("Principio", SpIcons.Compass, punto, "principio", punto.principio, Modifier.weight(1f), onUpdate)
                    MiniField("Pregunta de control", SpIcons.Info, punto, "pregunta", punto.pregunta, Modifier.weight(1f), onUpdate)
                }

                AngularPanel {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            SpIcon(SpIcons.Target, tint = accent.base, size = 15.dp)
                            Spacer(Modifier.width(8.dp))
                            Text("OBJETIVOS MICRO", style = SpType.panelTitle, color = accent.base)
                        }
                        GlowButton(text = "Agregar", onClick = { showAddMicro = true })
                    }
                    Spacer(Modifier.height(10.dp))
                    MicroList(punto = punto, onUpdate = onUpdate)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    AngularPanel(modifier = Modifier.weight(1f)) {
                        PanelTitle("Métrica de resultado", leading = { SpIcon(SpIcons.Target, tint = accent.base) })
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            TargetChart(value = punto.metricaResultadoValue / 100f, color = accent.base, size = 76.dp)
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                SpTextField(
                                    itemKey = "${punto.id}:resLabel", initialValue = punto.metricaResultadoLabel,
                                    onCommit = { onUpdate(punto.copy(metricaResultadoLabel = it)) },
                                    placeholder = "Ej: 78 kg de peso corporal", ghost = true, style = SpType.monoSm,
                                )
                                Spacer(Modifier.height(8.dp))
                                var sliderPos by remember(punto.id) { mutableStateOf(punto.metricaResultadoValue.toFloat()) }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Slider(
                                        value = sliderPos,
                                        onValueChange = { sliderPos = it; onUpdate(punto.copy(metricaResultadoValue = it.toInt())) },
                                        valueRange = 0f..100f,
                                        modifier = Modifier.weight(1f),
                                        colors = SliderDefaults.colors(thumbColor = accent.base, activeTrackColor = accent.base, inactiveTrackColor = SpInk2.copy(alpha = 0.25f)),
                                    )
                                    Text("${sliderPos.toInt()}%", style = SpType.monoSm, color = SpInk2, modifier = Modifier.width(36.dp))
                                }
                            }
                        }
                    }
                    AngularPanel(modifier = Modifier.weight(1f)) {
                        PanelTitle("Métrica de ejecución", leading = { SpIcon(SpIcons.Zap, tint = accent.base) })
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadarChart(labels = DIAS, values = punto.metricaEjecucion.map { it.toFloat() }, max = 1f, color = accent.base, size = 84.dp)
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                SpTextField(
                                    itemKey = "${punto.id}:ejLabel", initialValue = punto.metricaEjLabel,
                                    onCommit = { onUpdate(punto.copy(metricaEjLabel = it)) },
                                    placeholder = "Ej: 5 sesiones por semana", ghost = true, style = SpType.monoSm,
                                )
                                Spacer(Modifier.height(8.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    DIAS.forEachIndexed { i, d ->
                                        val on = punto.metricaEjecucion.getOrElse(i) { 0 } == 1
                                        DayChip(d, on) {
                                            val newList = punto.metricaEjecucion.toMutableList().also { l ->
                                                while (l.size < 7) l.add(0)
                                                l[i] = if (l[i] == 1) 0 else 1
                                            }
                                            onUpdate(punto.copy(metricaEjecucion = newList))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                GlowButton(
                    text = "Eliminar objetivo macro", onClick = { showDelete = true }, style = SpButtonStyle.Danger,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

    if (showDelete) {
        SpConfirmDialog(
            message = "¿Eliminar \"${punto.titulo}\"?",
            onDismiss = { showDelete = false },
            onConfirm = { onDelete(); showDelete = false },
        )
    }
    if (showAddMicro) {
        SpPromptDialog(
            title = "Nuevo objetivo micro", placeholder = "Ej: Reducir azúcar añadido a <20g/día",
            confirmLabel = "Agregar",
            onDismiss = { showAddMicro = false },
            onConfirm = { text ->
                val newMicro = punto.micro + com.centinela.app.sp.data.objetivos.MicroItem(
                    id = com.centinela.app.sp.data.spUid("mi"), text = text, done = false,
                )
                onUpdate(punto.copy(micro = newMicro))
                showAddMicro = false
            },
        )
    }
}

@Composable
private fun WhyAccordion(punto: PuntoEntity, onUpdate: (PuntoEntity) -> Unit) {
    var open by remember(punto.id) { mutableStateOf(true) }
    val accent = spAccent()
    val rotation by androidx.compose.animation.core.animateFloatAsState(if (open) 180f else 0f, label = "chev3")
    Column(
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(2.dp)).background(Color(0x66000000))
            .border(1.dp, accent.base.copy(alpha = 0.25f), RoundedCornerShape(2.dp)),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable { open = !open }.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SpIcon(SpIcons.Info, tint = accent.base, size = 14.dp)
            Spacer(Modifier.width(8.dp))
            Text("¿Por qué importa?", style = SpType.label.copy(fontSize = SpType.body.fontSize), color = SpInk0, modifier = Modifier.weight(1f))
            SpIcon(SpIcons.ChevDown, tint = accent.base, size = 14.dp, modifier = Modifier.rotate(rotation))
        }
        AnimatedVisibility(open, enter = fadeIn() + expandVertically(), exit = fadeOut() + shrinkVertically()) {
            Box(modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 12.dp)) {
                SpTextField(itemKey = "${punto.id}:porQue", initialValue = punto.porQue, onCommit = { onUpdate(punto.copy(porQue = it)) }, minLines = 4)
            }
        }
    }
}

@Composable
private fun MiniField(
    label: String, icon: ImageVector, punto: PuntoEntity, field: String, value: String,
    modifier: Modifier = Modifier, onUpdate: (PuntoEntity) -> Unit,
) {
    val accent = spAccent()
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            SpIcon(icon, tint = accent.base, size = 13.dp)
            Spacer(Modifier.width(6.dp))
            Text(label, style = SpType.monoLabel, color = SpInk2)
        }
        Spacer(Modifier.height(6.dp))
        SpTextField(
            itemKey = "${punto.id}:$field", initialValue = value, minLines = 3,
            onCommit = {
                onUpdate(
                    when (field) {
                        "conducta" -> punto.copy(conducta = it)
                        "principio" -> punto.copy(principio = it)
                        else -> punto.copy(pregunta = it)
                    }
                )
            },
        )
    }
}

@Composable
private fun MicroList(punto: PuntoEntity, onUpdate: (PuntoEntity) -> Unit) {
    val accent = spAccent()
    if (punto.micro.isEmpty()) {
        Text("Ej: Reducir azúcar añadido a <20g/día", style = SpType.body, color = SpInk2.copy(alpha = 0.6f))
        return
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        punto.micro.forEach { m ->
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(if (m.done) accent.base else Color.Transparent)
                        .border(1.dp, accent.base, RoundedCornerShape(3.dp))
                        .clickable {
                            val updated = punto.micro.map { if (it.id == m.id) it.copy(done = !it.done) else it }
                            onUpdate(punto.copy(micro = updated))
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    if (m.done) SpIcon(SpIcons.Check, tint = Color(0xFF050708), size = 12.dp)
                }
                Spacer(Modifier.width(10.dp))
                Text(
                    m.text, style = SpType.body,
                    color = if (m.done) SpInk2 else SpInk1,
                    modifier = Modifier.weight(1f),
                )
                SpIcon(
                    SpIcons.X, tint = SpInk2, size = 14.dp,
                    modifier = Modifier.clickable {
                        onUpdate(punto.copy(micro = punto.micro.filter { it.id != m.id }))
                    },
                )
            }
        }
    }
}

@Composable
private fun IndicadorTable(rows: List<String>, key: String, data: List<IndicadorEntity>, finalLabel: String, onCommit: (IndicadorEntity) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            HeaderCell("Indicador", 2f)
            HeaderCell("Línea base", 1f)
            HeaderCell("Meta", 1f)
            HeaderCell("Actual", 1f)
            HeaderCell(finalLabel, 1f)
            HeaderCell("¿Cumplido?", 1.2f)
        }
        rows.forEach { nombre ->
            val entity = data.find { it.nombre == nombre } ?: IndicadorEntity(key = key, nombre = nombre)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(2f)) { Text(nombre, style = SpType.body, color = SpInk1) }
                Box(modifier = Modifier.weight(1f).padding(horizontal = 4.dp)) {
                    SpTextField(itemKey = "$key:$nombre:base", initialValue = entity.base, style = SpType.monoSm, onCommit = { onCommit(entity.copy(base = it)) })
                }
                Box(modifier = Modifier.weight(1f).padding(horizontal = 4.dp)) {
                    SpTextField(itemKey = "$key:$nombre:meta", initialValue = entity.meta, style = SpType.monoSm, onCommit = { onCommit(entity.copy(meta = it)) })
                }
                Box(modifier = Modifier.weight(1f).padding(horizontal = 4.dp)) {
                    SpTextField(itemKey = "$key:$nombre:actual", initialValue = entity.actual, style = SpType.monoSm, onCommit = { onCommit(entity.copy(actual = it)) })
                }
                Box(modifier = Modifier.weight(1f).padding(horizontal = 4.dp)) {
                    SpTextField(itemKey = "$key:$nombre:final", initialValue = entity.final, style = SpType.monoSm, onCommit = { onCommit(entity.copy(final = it)) })
                }
                Row(modifier = Modifier.weight(1.2f), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CumplidoChip("Sí", entity.cumplido == "si") { onCommit(entity.copy(cumplido = if (entity.cumplido == "si") "" else "si")) }
                    CumplidoChip("No", entity.cumplido == "no") { onCommit(entity.copy(cumplido = if (entity.cumplido == "no") "" else "no")) }
                }
            }
        }
    }
}

@Composable
private fun RowScope.HeaderCell(text: String, weight: Float) {
    Box(modifier = Modifier.weight(weight)) {
        Text(text.uppercase(), style = SpType.monoSm, color = SpInk2)
    }
}

@Composable
private fun CumplidoChip(label: String, selected: Boolean, onClick: () -> Unit) {
    val accent = spAccent()
    Text(
        label, style = SpType.monoSm,
        color = if (selected) Color(0xFF050708) else SpInk2,
        modifier = Modifier
            .clip(RoundedCornerShape(3.dp))
            .background(if (selected) accent.base else Color.Transparent)
            .border(1.dp, accent.base.copy(alpha = if (selected) 1f else 0.35f), RoundedCornerShape(3.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 4.dp),
    )
}

@Composable
private fun DayChip(label: String, on: Boolean, onClick: () -> Unit) {
    val accent = spAccent()
    Box(
        modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(if (on) accent.base else Color.Transparent)
            .border(1.dp, accent.base.copy(alpha = if (on) 1f else 0.4f), CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(label, style = SpType.monoSm, color = if (on) Color(0xFF050708) else SpInk2)
    }
}

@Composable
private fun Tag(text: String, color: Color) {
    Text(
        text, style = SpType.monoSm, color = color,
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(color.copy(alpha = 0.10f))
            .border(1.dp, color.copy(alpha = 0.5f), RoundedCornerShape(2.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp),
    )
}

@Composable
private fun FieldLabel(text: String, icon: ImageVector) {
    val accent = spAccent()
    Row(verticalAlignment = Alignment.CenterVertically) {
        SpIcon(icon, tint = accent.base, size = 13.dp)
        Spacer(Modifier.width(6.dp))
        Text(text, style = SpType.monoLabel, color = SpInk2)
    }
}
