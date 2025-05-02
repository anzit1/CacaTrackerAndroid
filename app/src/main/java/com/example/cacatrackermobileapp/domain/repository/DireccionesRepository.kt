package com.example.cacatrackermobileapp.domain.repository

import com.example.cacatrackermobileapp.data.models.Direccion
import retrofit2.Response

interface DireccionesRepository {
    suspend fun getAllDirecciones(): Response<List<Direccion>>
}