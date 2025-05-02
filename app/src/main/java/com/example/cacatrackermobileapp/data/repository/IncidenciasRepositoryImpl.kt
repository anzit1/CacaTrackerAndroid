package com.example.cacatrackermobileapp.data.repository

import com.example.cacatrackermobileapp.data.api.RetrofitClient
import com.example.cacatrackermobileapp.data.models.CodigoPostalCountDTO
import com.example.cacatrackermobileapp.data.models.DireccionCountDTO
import com.example.cacatrackermobileapp.data.models.Incidencias
import com.example.cacatrackermobileapp.domain.repository.IncidenciasRepository

class IncidenciasRepositoryImpl : IncidenciasRepository {

    override suspend fun getAllIncidencias(): List<Incidencias> {
        val response = RetrofitClient.api.getAllIncidencias()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error: ${response.code()}")
        }
    }

    override suspend fun getIncidenciasByUserId(userId: Int): List<Incidencias> {
        val response = RetrofitClient.api.getIncidenciasByUserId(userId)
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    override suspend fun getTop5CpByUser(userId: Long): List<CodigoPostalCountDTO> {
        val response = RetrofitClient.api.getTop5CpByUser(userId.toInt())
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    override suspend fun getTop5DirByUser(userId: Long): List<DireccionCountDTO> {
        val response = RetrofitClient.api.getTop5DirByUser(userId.toInt())
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    override suspend fun getTop5CpGlobal(): List<CodigoPostalCountDTO> {
        val response = RetrofitClient.api.getTop5CpGlobal()
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    override suspend fun getTop5DirGlobal(): List<DireccionCountDTO> {
        val response = RetrofitClient.api.getTop5DirGlobal()
        return if (response.isSuccessful) response.body() ?: emptyList() else emptyList()
    }

    override suspend fun deleteIncidencia(id: Long): Boolean {
        val response = RetrofitClient.api.deleteIncidencia(id)
        return response.isSuccessful
    }

    override suspend fun createIncidencia(data: Map<String, String>): Map<String, String> {
        val response = RetrofitClient.api.createIncidencia(data)
        if (response.isSuccessful) {
            return response.body() ?: emptyMap()
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception(errorBody)
        }
    }
}