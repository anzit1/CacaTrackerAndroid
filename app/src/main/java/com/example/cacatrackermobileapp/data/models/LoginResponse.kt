package com.example.cacatrackermobileapp.data.models

data class LoginResponse(
    val idUser: Int,
    val EXITO: String,
    val token: String,
    val username: String
)