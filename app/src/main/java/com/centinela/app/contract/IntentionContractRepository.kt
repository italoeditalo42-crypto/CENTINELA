package com.centinela.app.contract

import kotlinx.coroutines.flow.Flow

class IntentionContractRepository(private val dao: IntentionContractDao) {
    suspend fun saveContract(text: String): Long {
        val contract = IntentionContract(contractText = text)
        return dao.insert(contract)
    }

    suspend fun getLatest(): IntentionContract? = dao.getLatest()

    fun getLatestFlow(): Flow<IntentionContract?> = dao.getLatestFlow()

    suspend fun markAsRead(id: Long) = dao.markAsRead(id)
}
