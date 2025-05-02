package com.example.cacatrackermobileapp.data.repository

import com.example.cacatrackermobileapp.data.api.RetrofitClient
import com.example.cacatrackermobileapp.data.models.LoginRequest
import com.example.cacatrackermobileapp.data.models.LoginResponse
import com.example.cacatrackermobileapp.domain.repository.UsersRepository
import retrofit2.Response

class UsersRepositoryImpl : UsersRepository {
    override suspend fun login(loginRequest: LoginRequest): Response<LoginResponse> {
        return RetrofitClient.api.login(loginRequest)
    }

    override suspend fun recuperaPassUser(data: Map<String, String>): Response<Map<String, String>> {
        return RetrofitClient.api.recuperaPassUser(data)
    }

    override suspend fun resetPassUser(data: Map<String, String>): Response<Map<String, String>> {
        return RetrofitClient.api.resetPassUser(data)
    }

    override suspend fun activaCuentaUser(data: Map<String, String>): Response<Map<String, String>> {
        return RetrofitClient.api.activaCuentaUser(data)
    }

    override suspend fun registrarUser(data: Map<String, String>): Response<Map<String, String>> {
        return RetrofitClient.api.registrarUser(data)
    }
}
