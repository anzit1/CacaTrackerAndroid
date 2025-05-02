package com.example.cacatrackermobileapp.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.databind.util.StdDateFormat
import java.util.Base64
import java.util.Date

data class Incidencias(
    var id: Long? = null,
    var direccion: String? = null,
    var codigopostal: String? = null,
    var nombreartistico: String? = null,
    var fechacreacion: Date? = null,

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
                foto = null
            }
        }
    }

    @JsonSetter("fechacreacion")
    fun setFechaCreacion(value: String?) {
        if (!value.isNullOrEmpty()) {
            try {
                // Assuming the date format is "yyyy-MM-dd"
                fechacreacion = StdDateFormat().parse(value)
            } catch (e: Exception) {
                fechacreacion = null // Handle parsing failure
            }
        }
    }
}