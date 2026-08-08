package com.centinela.app.sp.data.objetivos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.centinela.app.sp.data.SpDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ObjetivosUiState(
    val areas: List<AreaWithPuntos> = emptyList(),
    val fisicos: List<IndicadorEntity> = emptyList(),
    val rendimiento: List<IndicadorEntity> = emptyList(),
    val loading: Boolean = true,
)

class ObjetivosViewModel(app: Application) : AndroidViewModel(app) {
    private val db = SpDatabase.get(app)
    private val repo = ObjetivosRepository(db.areaDao(), db.puntoDao(), db.indicadorDao())

    val ui: StateFlow<ObjetivosUiState> = combine(
        repo.observeAreas(),
        repo.observeIndicadores("fisicos"),
        repo.observeIndicadores("rendimiento"),
    ) { areas, fisicos, rendimiento ->
        ObjetivosUiState(areas = areas, fisicos = fisicos, rendimiento = rendimiento, loading = false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ObjetivosUiState())

    init {
        viewModelScope.launch { repo.ensureSeeded() }
    }

    fun addArea(nombre: String) = viewModelScope.launch { repo.addArea(nombre) }
    fun deleteArea(area: AreaEntity) = viewModelScope.launch { repo.deleteArea(area) }
    fun addPunto(areaId: String, titulo: String) = viewModelScope.launch { repo.addPunto(areaId, titulo) }
    fun updatePunto(punto: PuntoEntity) = viewModelScope.launch { repo.updatePunto(punto) }
    fun deletePunto(punto: PuntoEntity) = viewModelScope.launch { repo.deletePunto(punto) }

    fun indicador(key: String, nombre: String, current: List<IndicadorEntity>): IndicadorEntity =
        current.find { it.nombre == nombre } ?: IndicadorEntity(key = key, nombre = nombre)

    fun setIndicador(indicador: IndicadorEntity) = viewModelScope.launch { repo.setIndicador(indicador) }
}
