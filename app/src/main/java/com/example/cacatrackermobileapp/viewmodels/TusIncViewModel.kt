package com.example.cacatrackermobileapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cacatrackermobileapp.data.api.RetrofitClient
import com.example.cacatrackermobileapp.data.models.Incidencias
import com.example.cacatrackermobileapp.data.models.UserSession
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TusIncViewModel : ViewModel() {

    private val _incidencias = MutableStateFlow<List<Incidencias>>(emptyList())
    val incidencias: StateFlow<List<Incidencias>> = _incidencias

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    val username = UserSession.username
    val userId = UserSession.id
    private val objectMapper = jacksonObjectMapper()

    fun check() {
        Log.e("SCREEN", "Tus incidnecias screen")
    }

    fun cargaIncidencias() {
        Log.e("ViewModel", "cargaIncidencias() called")
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = RetrofitClient.api.getIncidenciasByUserId(userId!!)
                if (response.isSuccessful) {
                    _incidencias.value = response.body() ?: emptyList()
                    Log.e("API", "Exito: ${response.message()}")
                } else {
                    Log.e("API", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Exception: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteIncidencia(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = RetrofitClient.api.deleteIncidencia(id)
                if (response.isSuccessful) {
                    _incidencias.value = _incidencias.value.filter { it.id != id }
                    Log.e("API", "Exito: ${response.message()}")
                    onSuccess()
                } else {
                    Log.e("API", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Exception: ${e.message}")

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _loading.value = false
            }
        }
    }
}