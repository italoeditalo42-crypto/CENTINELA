package com.centinela.app.sp.data.objetivos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AreaDao {
    @Transaction
    @Query("SELECT * FROM sp_areas ORDER BY orderIndex")
    fun observeAreasWithPuntos(): Flow<List<AreaWithPuntos>>

    @Query("SELECT COUNT(*) FROM sp_areas")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(area: AreaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(areas: List<AreaEntity>)

    @Update
    suspend fun update(area: AreaEntity)

    @Delete
    suspend fun delete(area: AreaEntity)

    @Query("SELECT COALESCE(MAX(orderIndex), -1) FROM sp_areas")
    suspend fun maxOrderIndex(): Int
}

@Dao
interface PuntoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(punto: PuntoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(puntos: List<PuntoEntity>)

    @Update
    suspend fun update(punto: PuntoEntity)

    @Delete
    suspend fun delete(punto: PuntoEntity)

    @Query("DELETE FROM sp_puntos WHERE areaId = :areaId")
    suspend fun deleteByArea(areaId: String)

    @Query("SELECT COALESCE(MAX(orderIndex), -1) FROM sp_puntos WHERE areaId = :areaId")
    suspend fun maxOrderIndex(areaId: String): Int
}

@Dao
interface IndicadorDao {
    @Query("SELECT * FROM sp_indicadores WHERE `key` = :key")
    fun observeByKey(key: String): Flow<List<IndicadorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(indicador: IndicadorEntity)
}
