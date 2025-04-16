package com.example.cacatrackermobileapp.data.models

import com.google.gson.annotations.SerializedName
import java.util.Objects

data class Users(
    val id: Int? = null,
    val username: String? = null,
    @SerializedName(value = "password")
    val password: String? = null,
    val codigopostal: String? = null,
    @SerializedName(value = "codigoactiva")
    val codigoactiva: String? = null,
    val email: String? = null,
    @SerializedName(value = "recuperapass")
    val recuperapass: String? = null,
    val activado: Boolean = false,
    val incidenciasCollection: List<Incidencias>? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        other as Users
        return username == other.username &&
                password == other.password &&
                codigopostal == other.codigopostal
    }

    override fun hashCode(): Int {
        return Objects.hash(username, password, codigopostal)
    }

    override fun toString(): String {
        return "User(username='$username', password='$password', codigoPosta='$codigopostal')"
    }
}