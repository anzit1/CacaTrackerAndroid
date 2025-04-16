package com.example.cacatrackermobileapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.mindrot.jbcrypt.BCrypt

class LoginViewModel: ViewModel() {

    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val checked = mutableStateOf(false)
    val isLoginSuccessful = mutableStateOf(false)

    fun login() {
        val hashedPassword = BCrypt.hashpw(password.value, BCrypt.gensalt())


    }


}