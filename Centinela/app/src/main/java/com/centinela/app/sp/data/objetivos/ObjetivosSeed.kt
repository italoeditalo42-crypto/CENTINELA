package com.centinela.app.sp.data.objetivos

import com.centinela.app.sp.data.spUid

private fun puntoSeed(
    areaId: String, orderIndex: Int, titulo: String, porQue: String, conducta: String, principio: String, pregunta: String,
) = PuntoEntity(
    id = spUid("pt"), areaId = areaId, orderIndex = orderIndex, titulo = titulo, porQue = porQue,
    conducta = conducta, principio = principio, pregunta = pregunta,
)

fun seedSaludFisicaPuntos(areaId: String): List<PuntoEntity> = listOf(
    puntoSeed(areaId, 0, "Optimizar mi alimentación",
        "La alimentación es el combustible y la materia prima del organismo. Cada célula, tejido y sistema depende de los nutrientes para producir energía, reparar daños, sintetizar hormonas, fortalecer el sistema inmunológico y mantener un funcionamiento óptimo. Entrenar sin una nutrición adecuada limita el rendimiento y el progreso. Alimentarme correctamente significa darle a mi cuerpo los recursos para construir un cuerpo sano.",
        "Apegarme a un plan alimenticio con objetivos definidos de calorías (kcal), macronutrientes, fibra y distribución de comidas.",
        "Mi cuerpo solo puede construir con los nutrientes que le doy.",
        "¿Mi alimentación estuvo alineada con mis objetivos esta semana?"),
    puntoSeed(areaId, 1, "Incrementar masa muscular y fuerza",
        "La masa muscular es uno de los tejidos más importantes para la salud y la longevidad. Un sistema muscular fuerte mejora el metabolismo, protege las articulaciones, aumenta la capacidad funcional y reduce el riesgo de lesiones y pérdida de independencia con el paso de los años. Desarrollar fuerza significa construir un cuerpo más resistente, eficiente y preparado para afrontar cualquier desafío físico.",
        "Apegarme a un plan de entrenamiento de calistenia orientado al desarrollo progresivo de hipertrofia y fuerza.",
        "La sobrecarga progresiva produce adaptación muscular y neuromuscular.",
        "¿Entrené con la intensidad y progresión planificadas?"),
    puntoSeed(areaId, 2, "Potenciar mi capacidad cardiovascular y respiratoria",
        "El corazón y los pulmones son el sistema que suministra oxígeno y energía a todo el organismo. Cuanto más eficientes sean, mayor será la resistencia física, la recuperación, la claridad mental y la capacidad para afrontar esfuerzos prolongados. Una buena condición cardiovascular también reduce significativamente el riesgo de enfermedades crónicas y favorece una vida más larga y activa.",
        "Apegarme a un plan de entrenamiento aeróbico progresivo basado en evidencia científica.",
        "El corazón se fortalece cuando trabaja de forma constante y progresiva.",
        "¿Cumplí el volumen aeróbico que me propuse?"),
    puntoSeed(areaId, 3, "Optimizar la funcionalidad del cuerpo",
        "Un cuerpo verdaderamente funcional no solo es fuerte: también se mueve con estabilidad, coordinación, equilibrio y amplitud de movimiento. Mantener estas capacidades permite realizar cualquier actividad con mayor eficiencia, disminuye el riesgo de lesiones y preserva la autonomía física durante toda la vida.",
        "Apegarme a un programa de movilidad, flexibilidad, equilibrio, coordinación y estabilidad que complemente mi entrenamiento principal.",
        "Un cuerpo que se mueve bien envejece mejor.",
        "¿Trabajé movilidad y estabilidad con la frecuencia establecida?"),
    puntoSeed(areaId, 4, "Recuperación y mantenimiento fisiológico",
        "El progreso físico no ocurre durante el entrenamiento, sino durante la recuperación. Es en ese período cuando el organismo repone energía, repara tejidos, fortalece músculos, regula hormonas y restablece el equilibrio del sistema nervioso. Sin una recuperación adecuada, el rendimiento disminuye y aumenta el riesgo de lesiones, enfermedad y agotamiento.",
        "Permitir que mi cuerpo disponga del tiempo y los recursos necesarios para recuperar la energía gastada, reparar el desgaste físico diario y restablecer el equilibrio del sistema nervioso.",
        "La recuperación convierte el esfuerzo en progreso.",
        "¿Dormí y descansé lo suficiente para recuperarme bien?"),
    puntoSeed(areaId, 5, "Higiene corporal",
        "La higiene corporal protege la piel, previene infecciones, disminuye la proliferación de microorganismos y contribuye al bienestar físico y social. Mantener el cuerpo limpio también refleja disciplina personal y respeto por uno mismo, favoreciendo una mejor imagen y calidad de vida.",
        "Mantener diariamente una adecuada higiene corporal y el cuidado de todas las partes del cuerpo.",
        "Cuidar mi cuerpo también es proteger mi salud.",
        "¿Mantuve una higiene corporal constante todos los días?"),
)

data class SeedArea(val nombre: String, val puntos: (String) -> List<PuntoEntity>)

val SEED_AREAS: List<SeedArea> = listOf(
    SeedArea("Salud Física") { areaId -> seedSaludFisicaPuntos(areaId) },
    SeedArea("Bienestar Mental") { emptyList() },
    SeedArea("Familia") { emptyList() },
    SeedArea("Aprendizaje") { emptyList() },
    SeedArea("Trabajo y Finanzas") { emptyList() },
    SeedArea("Desarrollo Personal") { emptyList() },
)

val IND_FISICOS = listOf("Peso", "Bíceps", "Pecho", "Cintura", "Cuádriceps", "Pantorrillas", "Antebrazo", "Altura")
val IND_RENDIMIENTO = listOf("Flexiones máximas", "Dominadas máximas", "Fondos máximos", "Tiempo de carrera", "Tiempo de plancha")
