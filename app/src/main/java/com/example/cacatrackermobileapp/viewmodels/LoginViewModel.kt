package com.example.cacatrackermobileapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.cacatrackermobileapp.data.api.RetrofitClient.apiService
import org.mindrot.jbcrypt.BCrypt

class LoginViewModel: ViewModel() {

    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val checked = mutableStateOf(false)
    val isLoginSuccessful = mutableStateOf(false)

    fun login() {

        val hashedPassword = BCrypt.hashpw(password.value, BCrypt.gensalt())

        try {
            // Make the login request
            val response = apiService.login(username.value, hashedPassword)

            if (response.isSuccessful) {
                // On success, handle the response
                isLoginSuccessful.value = true
                loginError.value = null
            } else {
                // On failure, show an error message
                isLoginSuccessful.value = false
                loginError.value = "Invalid username or password"
            }
        } catch (e: Exception) {
            // Handle any errors during the request
            isLoginSuccessful.value = false
            loginError.value = "Network error: ${e.message}"
        }

    }


}