package com.example.cacatrackermobileapp.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import java.util.Base64

data class Incidencias(
    var id: Long? = null,
    var direccion: String? = null,
    var codigopostal: String? = null,
    var nombreartistico: String? = null,

    @JsonProperty("idUsers")
    var idUsers: Users? = null
) {
    var foto: ByteArray? = null

    @JsonSetter("foto")
    fun setFoto(value: String?) {
        if (!value.isNullOrEmpty()) {
            try {
                foto = Base64.getDecoder().decode(value)
            } catch (e: IllegalArgumentException) {
                // Handle the case where the Base64 string is invalid
                foto = null
            }
        }
    }
}