package com.example.cacatrackermobileapp.domain.repository

import com.example.cacatrackermobileapp.data.models.LoginRequest
import com.example.cacatrackermobileapp.data.models.LoginResponse
import retrofit2.Response

interface UsersRepository {
    suspend fun login(loginRequest: LoginRequest): Response<LoginResponse>
    suspend fun recuperaPassUser(data: Map<String, String>): Response<Map<String, String>>
    suspend fun resetPassUser(data: Map<String, String>): Response<Map<String, String>>
    suspend fun activaCuentaUser(data: Map<String, String>): Response<Map<String, String>>
    suspend fun registrarUser(data: Map<String, String>): Response<Map<String, String>>
}