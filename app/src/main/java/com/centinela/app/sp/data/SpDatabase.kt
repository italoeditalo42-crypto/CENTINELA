package com.centinela.app.sp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.centinela.app.sp.data.objetivos.AreaDao
import com.centinela.app.sp.data.objetivos.AreaEntity
import com.centinela.app.sp.data.objetivos.IndicadorDao
import com.centinela.app.sp.data.objetivos.IndicadorEntity
import com.centinela.app.sp.data.objetivos.ObjetivosConverters
import com.centinela.app.sp.data.objetivos.PuntoDao
import com.centinela.app.sp.data.objetivos.PuntoEntity

/**
 * Nota de una carpeta/entrada rápida — placeholder mínimo que valida el patrón
 * Room + Repository del que dependerán Objetivos, Ejecución, Evolución y
 * Biblioteca en las siguientes fases. No se usa todavía desde ninguna pantalla
 * de esta entrega (los módulos de Fase 2 son de solo lectura).
 */
@Entity(tableName = "sp_notes")
data class SpNoteEntity(
    @PrimaryKey val id: String,
    val createdAt: Long,
    val text: String,
)

@androidx.room.Dao
interface SpNoteDao {
    @androidx.room.Query("SELECT * FROM sp_notes ORDER BY createdAt DESC")
    fun observeAll(): kotlinx.coroutines.flow.Flow<List<SpNoteEntity>>

    @androidx.room.Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun upsert(note: SpNoteEntity)

    @androidx.room.Delete
    suspend fun delete(note: SpNoteEntity)
}

@Database(
    entities = [SpNoteEntity::class, AreaEntity::class, PuntoEntity::class, IndicadorEntity::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(ObjetivosConverters::class)
abstract class SpDatabase : RoomDatabase() {
    abstract fun noteDao(): SpNoteDao
    abstract fun areaDao(): AreaDao
    abstract fun puntoDao(): PuntoDao
    abstract fun indicadorDao(): IndicadorDao

    companion object {
        @Volatile private var INSTANCE: SpDatabase? = null

        fun get(context: Context): SpDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, SpDatabase::class.java, "sistema_personal.db"
                ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
            }
    }
}
