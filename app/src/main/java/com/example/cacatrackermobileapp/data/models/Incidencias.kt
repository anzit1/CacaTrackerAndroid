package com.example.cacatrackermobileapp.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Base64

data class Incidencias(
    var id: Long? = null,
    var direccion: String? = null,
    var codigopostal: String? = null,
    var nombreartistico: String? = null,
    var foto: ByteArray? = null,

    @JsonProperty("idUsers")
    var idUsers: Users? = null
) {
    var fotoBase64: String? = null
        set(value) {
            field = value

            if (!value.isNullOrEmpty()) {
                foto = Base64.getDecoder().decode(value)
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Incidencias

        if (id != other.id) return false
        if (direccion != other.direccion) return false
        if (codigopostal != other.codigopostal) return false
        if (nombreartistico != other.nombreartistico) return false
        if (foto != null) {
            if (other.foto == null) return false
            if (!foto.contentEquals(other.foto)) return false
        } else if (other.foto != null) return false
        if (idUsers != other.idUsers) return false
        if (fotoBase64 != other.fotoBase64) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (direccion?.hashCode() ?: 0)
        result = 31 * result + (codigopostal?.hashCode() ?: 0)
        result = 31 * result + (nombreartistico?.hashCode() ?: 0)
        result = 31 * result + (foto?.contentHashCode() ?: 0)
        result = 31 * result + (idUsers?.hashCode() ?: 0)
        result = 31 * result + (fotoBase64?.hashCode() ?: 0)
        return result
    }
}
