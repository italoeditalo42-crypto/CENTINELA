package com.centinela.app.sp.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.spDataStore by preferencesDataStore(name = "sistema_personal")

/**
 * Equivalente a storage.js (load/save por clave namespaced) pero respaldado por
 * Jetpack DataStore en vez de localStorage. Usado para ajustes simples y
 * contenido de solo lectura editable (Constitución, Identidad, Antiidentidad,
 * Dirección). Los datos relacionales (Objetivos, Ejecución, Evolución,
 * Biblioteca) usan Room — ver SpDatabase.kt.
 */
class SpSettingsRepository(private val context: Context) {

    fun stringFlow(key: String, fallback: String = ""): Flow<String> {
        val prefKey = stringPreferencesKey(key)
        return context.spDataStore.data.map { it[prefKey] ?: fallback }
    }

    suspend fun setString(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        context.spDataStore.edit { it[prefKey] = value }
    }

    suspend fun remove(key: String) {
        val prefKey = stringPreferencesKey(key)
        context.spDataStore.edit { it.remove(prefKey) }
    }
}

/** Equivalente a uid() de storage.js */
fun spUid(prefix: String = "id"): String {
    val time = java.lang.Long.toString(System.currentTimeMillis(), 36)
    val alphabet = "abcdefghijklmnopqrstuvwxyz0123456789"
    val rand = (1..5).map { alphabet.random() }.joinToString("")
    return "${prefix}_${time}${rand}"
}
