package com.centinela.app.contract

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IntentionContractDao {
    @Insert
    suspend fun insert(contract: IntentionContract): Long

    @Query("SELECT * FROM intention_contracts ORDER BY createdAt DESC LIMIT 1")
    suspend fun getLatest(): IntentionContract?

    @Query("SELECT * FROM intention_contracts ORDER BY createdAt DESC LIMIT 1")
    fun getLatestFlow(): Flow<IntentionContract?>

    @Query("UPDATE intention_contracts SET wasRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Long)
}
