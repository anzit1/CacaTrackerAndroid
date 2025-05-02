package com.example.cacatrackermobileapp.data.repository

import com.example.cacatrackermobileapp.data.api.RetrofitClient
import com.example.cacatrackermobileapp.data.models.Direccion
import com.example.cacatrackermobileapp.domain.repository.DireccionesRepository
import retrofit2.Response

class DireccionesRepositoryImpl : DireccionesRepository {
    override suspend fun getAllDirecciones(): Response<List<Direccion>> {
        return RetrofitClient.api.getAllDirecciones()
    }
}