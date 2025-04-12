package com.example.cacatrackermobileapp.data.models

import java.util.Objects

data class Direccion(
    var id: Long? = null,
    var direccion: String? = null,
    var codigoPostal: String? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Direccion
        return direccion == other.direccion && codigoPostal == other.codigoPostal
    }

    override fun hashCode(): Int {
        return Objects.hash(direccion, codigoPostal)
    }

    override fun toString(): String {
        return "Direcciones(id=$id, direccion=$direccion, codigoPostal=$codigoPostal)"
    }
}