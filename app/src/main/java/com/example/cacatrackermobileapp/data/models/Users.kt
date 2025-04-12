package com.example.cacatrackermobileapp.data.models

import java.util.Objects

data class Users(
    var id: Int? = null,
    var username: String? = null,
    var password: String? = null,
    var codigopostal: String? = null,
    var codigoactiva: String? = null,
    var email: String? = null,
    var recuperapass: String? = null,
    var activado: Boolean = false,
    var incidenciasCollection: List<Incidencias>? = null
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