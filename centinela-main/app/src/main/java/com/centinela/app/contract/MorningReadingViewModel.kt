package com.centinela.app.contract

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MorningReadingViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = IntentionContractRepository(
        CentinelaDatabase.getInstance(app).intentionContractDao()
    )

    private val _contract = MutableStateFlow<IntentionContract?>(null)
    val contract: StateFlow<IntentionContract?> = _contract

    private val _readingDone = MutableStateFlow(false)
    val readingDone: StateFlow<Boolean> = _readingDone

    init {
        viewModelScope.launch {
            _contract.value = repo.getLatest()
        }
    }

    fun markAsRead(id: Long) {
        viewModelScope.launch {
            repo.markAsRead(id)
            _readingDone.value = true
        }
    }
}
