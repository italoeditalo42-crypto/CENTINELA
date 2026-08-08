package com.centinela.app.contract

import androidx.room.*

@Entity(tableName = "intention_contracts")
data class IntentionContract(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val contractText: String,
    val createdAt: Long = System.currentTimeMillis(),
    val wasRead: Boolean = false,
    val wasHonored: Boolean? = null,
    val honoredNote: String? = null
)
