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
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class LoginViewModel : ViewModel() {

    val username = mutableStateOf("")
    val password = mutableStateOf("")

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    val _loginTitle = mutableStateOf("")
    val _loginError = mutableStateOf<String?>(null)
    val loginError: State<String?> = _loginError

    val showActivationDialog = mutableStateOf(false)
    val activationCode = mutableStateOf("")

    val resetPasswordMessage = mutableStateOf<String?>(null)

    val isLoading = mutableStateOf(false)

    fun login() {
        _loginError.value = null

        if (username.value.isBlank() || password.value.isBlank()) {
            _loginError.value = "Rellena todos los campos para login."
            return
        }

        viewModelScope.launch {
            try {
                isLoading.value = true
                val loginRequest = LoginRequest(username.value, password.value)
                val response = RetrofitClient.api.login(loginRequest)

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    when {
                        responseBody?.EXITO!!.contains("OKLOGIN") -> {
                            _loginSuccess.value = true
                            _loginError.value = null
                            UserSession.username = responseBody.username
                            UserSession.id = responseBody.idUser
                            UserSession.token = responseBody.token
                        }
                        responseBody.EXITO.contains("KOLOGIN") -> {
                            _loginError.value = "Error en las credenciales"
                        }
                        else -> {
                            _loginError.value = responseBody.EXITO
                        }
                    }
                }else if(response.code() == 403){
                    // Cuenta no activada
                    _loginTitle.value = "Cuenta no activada"
                    _loginError.value = "Tu cuenta no está activada. En tu correo está el codigo de activación"
                    showActivationDialog.value = true
                } else{
                    when (response.code()) {
                        401 -> {
                            _loginTitle.value = "Error"
                            _loginError.value = "Credenciales incorrectas"
                        }
                        500 -> {
                            _loginTitle.value = "Error"
                            _loginError.value = "Error de servidor"
                        }
                        else -> {
                            _loginTitle.value = "Error"
                            _loginError.value = "Error login -> (code ${response.code()})"
                        }
                    }
                }
            } catch (e: Exception) {
                _loginError.value = when (e) {
                    is ConnectException -> "No se ha podido connectar al servidor"
                    is SocketTimeoutException -> "Timeout conexion"
                    is IOException -> "Error de Network"
                    else -> "Un error ha ocurrido -> ${e.localizedMessage}"
                }
                Log.e("Login", "Erro en el login", e)
            }finally {
                isLoading.value = false
            }
        }
    }

    fun requestPasswordReset(email: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val requestData = mapOf("email" to email)
                val response = RetrofitClient.api.recuperaPassUser(requestData)

                if (response.isSuccessful) {
                    response.body()?.let { responseBody ->
                        if (responseBody.containsKey("ERROR")) {
                            resetPasswordMessage.value = responseBody["ERROR"] ?: "Error al enviar el correo"
                        } else {
                            resetPasswordMessage.value = "Correo de recuperación enviado. Revise su bandeja de entrada."
                        }
                    } ?: run {
                        resetPasswordMessage.value = "Respuesta vacía del servidor"
                    }
                } else {
                    resetPasswordMessage.value = "Error del servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                resetPasswordMessage.value = when (e) {
                    is SocketTimeoutException -> "Tiempo de espera agotado"
                    is ConnectException -> "No se pudo conectar al servidor"
                    is IOException -> "Error de red"
                    else -> "Error inesperado: ${e.localizedMessage}"
                }
            }finally {
                isLoading.value = false
            }
        }
    }

    fun activateAccount(onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val requestData = mapOf(
                    "username" to username.value,
                    "codigoactiva" to activationCode.value
                )
                val response = RetrofitClient.api.activaCuentaUser(requestData)
                if (response.isSuccessful) {
                    onSuccess()
                    _loginError.value = null
                    showActivationDialog.value = false
                } else {
                    onError("Error al activar la cuenta: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("Error al activar: ${e.localizedMessage}")
            } finally {
                isLoading.value = false
            }
        }
    }
}

