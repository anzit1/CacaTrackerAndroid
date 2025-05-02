package com.example.cacatrackermobileapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cacatrackermobileapp.data.api.RetrofitClient.api
import com.example.cacatrackermobileapp.data.models.CodigoPostalCountDTO
import com.example.cacatrackermobileapp.data.models.DireccionCountDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstadisticasViewModel: ViewModel() {

    private val _codigoPostalData = MutableStateFlow<List<CodigoPostalCountDTO>>(emptyList())
    val codigoPostalData: StateFlow<List<CodigoPostalCountDTO>> = _codigoPostalData

    private val _direccionData = MutableStateFlow<List<DireccionCountDTO>>(emptyList())
    val direccionData: StateFlow<List<DireccionCountDTO>> = _direccionData

    fun fetchTop5CpByUser(userId: Int) {
        viewModelScope.launch {
            try {
                val response = api.getTop5CpByUser(userId)
                Log.d("API Response", "Raw API Response: $response")
                if (response.isSuccessful) {
                    _codigoPostalData.value = response.body().orEmpty()
                    Log.d("ViewModel", "Fetched Data: ${_codigoPostalData.value}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching CP by user: ${e.message}")
            }
        }
    }

    fun fetchTop5DirByUser(userId: Int) {
        viewModelScope.launch {
            try {
                val response = api.getTop5DirByUser(userId)
                Log.d("API Response", "Raw API Response: $response")
                if (response.isSuccessful) {
                    _direccionData.value = response.body().orEmpty()
                    Log.d("ViewModel", "Fetched Data: ${_direccionData.value}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching Dir by user: ${e.message}")
            }
        }
    }

    fun fetchTop5CpGlobal() {
        viewModelScope.launch {
            try {
                val response = api.getTop5CpGlobal()
                Log.d("API Response", "Raw API Response: $response")
                if (response.isSuccessful) {
                    _codigoPostalData.value = response.body().orEmpty()
                    Log.d("ViewModel", "Fetched Data: ${_codigoPostalData.value}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching global CP: ${e.message}")
            }
        }
    }

    fun fetchTop5DirGlobal() {
        viewModelScope.launch {
            try {
                val response = api.getTop5DirGlobal()
                Log.d("API Response", "Raw API Response: $response")
                if (response.isSuccessful) {
                    _direccionData.value = response.body().orEmpty()
                    Log.d("ViewModel", "Fetched Data: ${_direccionData.value}")
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching global Dir: ${e.message}")
            }
        }
    }
}