package com.example.cacatrackermobileapp.data.models

import java.util.Date

data class IncidenciasDTO(
    val id: Long,
    val direccion: String,
    val codigopostal: String,
    val nombreartistico: String,
    val foto: ByteArray? = null,
    val fechacreacion: Date? = null
)
