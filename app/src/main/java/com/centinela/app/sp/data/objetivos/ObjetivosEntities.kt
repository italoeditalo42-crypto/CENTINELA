package com.centinela.app.sp.data.objetivos

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class MicroItem(val id: String, val text: String, val done: Boolean = false)

@Entity(tableName = "sp_areas")
data class AreaEntity(
    @PrimaryKey val id: String,
    val nombre: String,
    val orderIndex: Int,
)

@Entity(tableName = "sp_puntos")
data class PuntoEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(index = true) val areaId: String,
    val orderIndex: Int,
    val titulo: String,
    val porQue: String = "",
    val conducta: String = "",
    val principio: String = "",
    val pregunta: String = "",
    val micro: List<MicroItem> = emptyList(),
    val metricaResultadoLabel: String = "",
    val metricaResultadoValue: Int = 0,
    val metricaEjLabel: String = "",
    val metricaEjecucion: List<Int> = listOf(0, 0, 0, 0, 0, 0, 0),
)

data class AreaWithPuntos(
    @Embedded val area: AreaEntity,
    @Relation(parentColumn = "id", entityColumn = "areaId")
    val puntos: List<PuntoEntity>,
)

/** key = "fisicos" | "rendimiento"; fila identificada por (key, nombre) */
@Entity(tableName = "sp_indicadores", primaryKeys = ["key", "nombre"])
data class IndicadorEntity(
    val key: String,
    val nombre: String,
    val base: String = "",
    val meta: String = "",
    val actual: String = "",
    val final: String = "",
    val cumplido: String = "", // "" | "si" | "no"
)

class ObjetivosConverters {
    private val gson = Gson()
    private val microType = object : TypeToken<List<MicroItem>>() {}.type
    private val intListType = object : TypeToken<List<Int>>() {}.type

    @TypeConverter
    fun fromMicroList(list: List<MicroItem>): String = gson.toJson(list)

    @TypeConverter
    fun toMicroList(json: String): List<MicroItem> =
        if (json.isBlank()) emptyList() else gson.fromJson(json, microType)

    @TypeConverter
    fun fromIntList(list: List<Int>): String = gson.toJson(list)

    @TypeConverter
    fun toIntList(json: String): List<Int> =
        if (json.isBlank()) emptyList() else gson.fromJson(json, intListType)
}
