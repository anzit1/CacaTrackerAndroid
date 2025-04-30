package com.example.cacatrackermobileapp.utils

import java.security.SecureRandom

class GeneradorCodigo {

    fun generaCodigo(): String {
        val random = SecureRandom()
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val code = StringBuilder(10)
        for (i in 0..9) {
            code.append(chars[random.nextInt(chars.length)])
        }
        return code.toString()
    }
}