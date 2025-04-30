package com.example.cacatrackermobileapp.data.models

data class IncidenciasDTO(
    val id: Long,
    val direccion: String,
    val codigopostal: String,
    val nombreartistico: String,
    val foto: ByteArray? = null
)
