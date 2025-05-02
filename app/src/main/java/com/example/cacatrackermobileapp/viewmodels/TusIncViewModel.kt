package com.example.cacatrackermobileapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cacatrackermobileapp.data.api.RetrofitClient
import com.example.cacatrackermobileapp.data.models.Incidencias
import com.example.cacatrackermobileapp.data.models.UserSession
import com.example.cacatrackermobileapp.data.repository.IncidenciasRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TusIncViewModel : ViewModel() {

    private val repository = IncidenciasRepositoryImpl()

    private val _incidencias = MutableStateFlow<List<Incidencias>>(emptyList())
    val incidencias: StateFlow<List<Incidencias>> = _incidencias

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    val username = UserSession.username
    val userId = UserSession.id

    fun cargaIncidencias() {

        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getIncidenciasByUserId(userId!!)
                _incidencias.value = response
            } catch (e: Exception) {
                Log.e("API", "Exception: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun sortIncidenciasByFechaCreacion() {
        _incidencias.value = _incidencias.value.sortedBy { it.fechacreacion }
    }

    fun sortIncidenciasByNombreArtistico() {
        _incidencias.value = _incidencias.value.sortedBy { it.nombreartistico?.lowercase() }
    }

    fun sortIncidenciasByCodigoPostal() {
        _incidencias.value = _incidencias.value.sortedBy { it.codigopostal }
    }

    fun deleteIncidencia(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val success = repository.deleteIncidencia(id)
                if (success) {
                    _incidencias.value = _incidencias.value.filter { it.id != id }
                    onSuccess()
                } else {
                    Log.e("API", "Error al eliminar incidencia")
                }
            } catch (e: Exception) {
                Log.e("API", "Exception: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }
}