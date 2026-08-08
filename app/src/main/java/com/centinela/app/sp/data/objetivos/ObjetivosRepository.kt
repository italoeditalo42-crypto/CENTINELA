package com.centinela.app.sp.data.objetivos

import com.centinela.app.sp.data.spUid
import kotlinx.coroutines.flow.Flow

class ObjetivosRepository(
    private val areaDao: AreaDao,
    private val puntoDao: PuntoDao,
    private val indicadorDao: IndicadorDao,
) {
    fun observeAreas(): Flow<List<AreaWithPuntos>> = areaDao.observeAreasWithPuntos()

    suspend fun ensureSeeded() {
        if (areaDao.count() > 0) return
        SEED_AREAS.forEachIndexed { i, seed ->
            val areaId = spUid("ar")
            areaDao.insert(AreaEntity(id = areaId, nombre = seed.nombre, orderIndex = i))
            val puntos = seed.puntos(areaId)
            if (puntos.isNotEmpty()) puntoDao.insertAll(puntos)
        }
    }

    suspend fun addArea(nombre: String) {
        val order = areaDao.maxOrderIndex() + 1
        areaDao.insert(AreaEntity(id = spUid("ar"), nombre = nombre, orderIndex = order))
    }

    suspend fun deleteArea(area: AreaEntity) {
        puntoDao.deleteByArea(area.id)
        areaDao.delete(area)
    }

    suspend fun addPunto(areaId: String, titulo: String) {
        val order = puntoDao.maxOrderIndex(areaId) + 1
        puntoDao.insert(PuntoEntity(id = spUid("pt"), areaId = areaId, orderIndex = order, titulo = titulo))
    }

    suspend fun updatePunto(punto: PuntoEntity) = puntoDao.update(punto)

    suspend fun deletePunto(punto: PuntoEntity) = puntoDao.delete(punto)

    fun observeIndicadores(key: String): Flow<List<IndicadorEntity>> = indicadorDao.observeByKey(key)

    suspend fun setIndicador(indicador: IndicadorEntity) = indicadorDao.upsert(indicador)
}
