package com.example.cacatrackermobileapp.data.api

import com.example.cacatrackermobileapp.data.models.CodigoPostalCountDTO
import com.example.cacatrackermobileapp.data.models.Direccion
import com.example.cacatrackermobileapp.data.models.DireccionCountDTO
import com.example.cacatrackermobileapp.data.models.Incidencias
import com.example.cacatrackermobileapp.data.models.LoginRequest
import com.example.cacatrackermobileapp.data.models.LoginResponse
import com.example.cacatrackermobileapp.data.models.Users

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //
    // USERS
    //
    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<Users>

    @GET("users")
    suspend fun getUsers(): List<Users>

    @POST("users/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>

    @POST("users/resetpassword")
    suspend fun recuperaPassUser(@Body data: Map<String, String>): Response<Map<String, String>>

    @POST("users/resetcheckfinalpassword")
    suspend fun resetPassUser(@Body data: Map<String, String>): Response<Map<String, String>>

    @POST("users/activacuenta")
    suspend fun activaCuentaUser(@Body data: Map<String, String>): Response<Map<String, String>>

    @POST("users/registrar")
    suspend fun registrarUser(@Body data: Map<String, String>): Response<Map<String, String>>

    //
    // INCIDENCIAS
    //
    @GET("incidencias")
    suspend fun getAllIncidencias(): Response<List<Incidencias>>

    @GET("incidencias/user/{id}")
    suspend fun getIncidenciasByUserId(@Path("id") userId: Int): Response<List<Incidencias>>

    @GET("incidencias/top5cp/user/{id}")
    suspend fun getTop5CpByUser(@Path("id") userId: Int): Response<List<CodigoPostalCountDTO>>

    @GET("incidencias/top5dir/user/{id}")
    suspend fun getTop5DirByUser(@Path("id") userId: Int): Response<List<DireccionCountDTO>>

    @GET("incidencias/top5cp")
    suspend fun getTop5CpGlobal(): Response<List<CodigoPostalCountDTO>>

    @GET("incidencias/top5dir")
    suspend fun getTop5DirGlobal(): Response<List<DireccionCountDTO>>

    @POST("incidencias/nuevaincidencia")
    suspend fun createIncidencia(@Body data: Map<String, String>): Response<Map<String, String>>

    @DELETE("incidencias/deleteincidencia/{id}")
    suspend fun deleteIncidencia(@Path("id") id: Long): Response<Map<String, String>>

    //
    // DIRECCIONES
    //
    @GET("direcciones")
    suspend fun getAllDirecciones(): Response<List<Direccion>>

}