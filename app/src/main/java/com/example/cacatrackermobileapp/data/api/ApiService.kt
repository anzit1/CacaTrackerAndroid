package com.example.cacatrackermobileapp.data.api

import com.example.cacatrackermobileapp.data.models.LoginRequest
import com.example.cacatrackermobileapp.data.models.LoginResponse
import com.example.cacatrackermobileapp.data.models.Users

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<Users>

    @GET("users")
    suspend fun getUsers(): List<Users>

    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

}