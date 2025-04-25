package com.example.cacatrackermobileapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cacatrackermobileapp.data.api.RetrofitClient
import com.example.cacatrackermobileapp.data.models.Users
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.cacatrackermobileapp.data.models.LoginRequest
import com.example.cacatrackermobileapp.data.models.UserSession
import org.mindrot.jbcrypt.BCrypt

class LoginViewModel : ViewModel() {

    // UI STATE
    val username = mutableStateOf("")
    val password = mutableStateOf("")
    val checked = mutableStateOf(false)

    // INTERNAL STATE
    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    private val _loginError = mutableStateOf<String?>(null)
    val loginError: State<String?> = _loginError

    val users = mutableStateListOf<Users>()

    fun login() {
        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(username.value, password.value)
                val response = RetrofitClient.api.login(loginRequest)
                Log.d("API Response", response.body().toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.EXITO == "OKLOGIN: Login successful") {
                        _loginSuccess.value = true
                        _loginError.value = null
                        val user = responseBody
                        val token = responseBody.token

                        // Store the user data and token
                        UserSession.username = user?.username
                        UserSession.id = user?.idUser
                        UserSession.token = token

                        Log.d("Login", "Login success, token: $token")
                    } else {
                        _loginSuccess.value = false
                        _loginError.value = responseBody?.EXITO ?: "Invalid credentials"
                        Log.d("Login", "Login failed: ${responseBody?.EXITO}")
                    }
                } else {
                    _loginSuccess.value = false
                    _loginError.value = "Login failed, server error"
                }
            } catch (e: Exception) {
                _loginSuccess.value = false
                _loginError.value = "Network or server error"
                Log.e("Login", "Exception: ", e)
            }
        }
    }
    }