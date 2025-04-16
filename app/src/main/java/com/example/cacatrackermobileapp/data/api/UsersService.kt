package com.example.cacatrackermobileapp.data.api

import com.example.cacatrackermobileapp.data.models.Users
import com.example.cacatrackermobileapp.data.models.UsersResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsersService {

    //Sacar un user
    //
    @GET("/users")
    suspend fun getUsuarios(): Response<UsersResponse>

    // # REGISTRAR USUARIO
    //
    @POST("/users/registrar")
    suspend fun registrarUsuario(
        @Body user: Users
    ): Response<UsersResponse>

    // # LOGIN USUARIO
    //
    @POST("/users/login")
    suspend fun loginUsuario(
        @Body user: Users
    ): Response<UsersResponse>

    // # ENVIA CORREO PARA RESET PASS DE USUARIO
    //
    @POST("/users/resetpassword")
    suspend fun getUpdatePassword(): Response<UsersResponse>

    // # RESET PASS DE USUARIO
    //
    @POST("/users/resetcheckfinalpassword")
    suspend fun getRegistrarUsuario(
        @Body user: Users
    ): Response<UsersResponse>

    // # ACTIVA CUENTA USUARIO
    //
    @POST("/users/activacuenta")
    suspend fun activaUsuario(
        @Body user: Users
    ): Response<UsersResponse>

}