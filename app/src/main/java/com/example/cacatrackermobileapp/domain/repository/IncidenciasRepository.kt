package com.example.cacatrackermobileapp.domain.repository

import com.example.cacatrackermobileapp.data.models.CodigoPostalCountDTO
import com.example.cacatrackermobileapp.data.models.DireccionCountDTO
import com.example.cacatrackermobileapp.data.models.Incidencias

interface IncidenciasRepository {
    suspend fun getAllIncidencias(): List<Incidencias>
    suspend fun getIncidenciasByUserId(userId: Int): List<Incidencias>
    suspend fun getTop5CpByUser(userId: Long): List<CodigoPostalCountDTO>
    suspend fun getTop5DirByUser(userId: Long): List<DireccionCountDTO>
    suspend fun getTop5CpGlobal(): List<CodigoPostalCountDTO>
    suspend fun getTop5DirGlobal(): List<DireccionCountDTO>
    suspend fun deleteIncidencia(id: Long): Boolean
    suspend fun createIncidencia(data: Map<String, String>): Map<String, String>
}
