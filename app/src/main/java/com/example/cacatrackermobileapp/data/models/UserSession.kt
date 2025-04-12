package com.example.cacatrackermobileapp.data.models

object UserSession {
    var username: String? = null
    var id: Int? = null
    var token: String? = null

    val userName: String
        get() = username ?: "Guest"

    fun clearUserInfo() {
        username = null
        token = null
        id = null
    }
}
