package com.centinela.app.sp.modules.constitucion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.centinela.app.sp.ui.components.ModuleBanner
import com.centinela.app.sp.ui.components.SpAccordionItem
import com.centinela.app.sp.ui.components.SpIcon
import com.centinela.app.sp.ui.icons.SpIcons
import com.centinela.app.sp.ui.theme.SpInk2
import com.centinela.app.sp.ui.theme.SpType
import com.centinela.app.sp.ui.theme.spAccent

private val ARTICULOS = listOf(
    "Primacía de la realidad" to "La realidad tiene prioridad sobre mis interpretaciones, deseos o expectativas. Cuando mi percepción entra en conflicto con la evidencia, la evidencia gana. No niego, minimizo ni distorsiono los hechos para que encajen con lo que prefiero creer.",
    "Congruencia" to "Mis decisiones, mis palabras y mis acciones deben apuntar en la misma dirección. La congruencia no es perfección: es que lo que pienso, digo y hago no se contradigan de forma sostenida.",
    "Integridad de la palabra" to "Mi palabra es un compromiso, no una intención. Si digo que haré algo, lo hago. Si no puedo cumplirlo, lo comunico a tiempo y con honestidad en lugar de guardar silencio.",
    "Responsabilidad" to "Soy responsable de mis decisiones y de sus consecuencias, incluso cuando las circunstancias externas influyeron. Buscar culpables no cambia el resultado; buscar soluciones sí.",
    "Mejora continua" to "No existe un estado final de \"llegar\". Cada ciclo —diario, semanal, mensual— es una oportunidad para corregir el rumbo, refinar el sistema y acercarme un poco más a la persona que decidí ser.",
    "La salud como condición" to "La salud física y mental no es un lujo ni una meta aislada: es la infraestructura que sostiene todo lo demás. Sin ella, ninguna otra prioridad es sostenible en el tiempo.",
    "Decisión consciente" to "Toda decisión relevante pasa, en la medida que su impacto lo justifique, por un proceso deliberado en lugar de una reacción automática. Pensar antes de actuar no es lentitud; es precisión.",
    "Responsabilidad sobre la acción" to "El conocimiento sin acción no produce cambio. Soy responsable no solo de saber qué es correcto, sino de ejecutarlo de forma consistente, incluso cuando resulte incómodo.",
    "Modificación de la Constitución" to "Esta Constitución no es inmutable, pero tampoco es negociable por conveniencia momentánea. Se modifica solo tras una revisión consciente, nunca en caliente ni bajo el impulso de una emoción pasajera.",
    "Resolución de conflictos" to "Cuando dos principios entran en tensión, se resuelve priorizando el que sostiene la vida a largo plazo sobre el que ofrece alivio inmediato, y consultando la Dirección como criterio de desempate.",
)

@Composable
fun ConstitucionScreen() {
    val accent = spAccent()
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        item {
            ModuleBanner(
                title = "MIS PRINCIPIOS FUNDAMENTALES",
                roman = "I",
                sub = "Los principios que rigen mis decisiones y mi vida.",
                glyph = { SpIcon(icon = SpIcons.Scale, tint = accent.base, size = 46.dp) },
            )
            androidx.compose.foundation.layout.Spacer(Modifier.padding(top = 6.dp))
        }
        ARTICULOS.forEachIndexed { i, (title, body) ->
            item {
                SpAccordionItem(
                    tag = "ART. ${(i + 1).toString().padStart(2, '0')}",
                    label = title,
                    body = body,
                )
            }
        }
        item {
            Box(modifier = Modifier.fillMaxSize().padding(top = 8.dp), contentAlignment = Alignment.Center) {
                Text(
                    "◆ MIS PRINCIPIOS · MI NORTE ◆",
                    style = SpType.monoSm,
                    color = SpInk2,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}
