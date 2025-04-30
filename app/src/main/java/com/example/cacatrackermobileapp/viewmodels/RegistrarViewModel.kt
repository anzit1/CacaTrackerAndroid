package com.example.cacatrackermobileapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cacatrackermobileapp.data.api.RetrofitClient
import com.example.cacatrackermobileapp.utils.GeneradorCodigo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.security.SecureRandom

class RegistrarViewModel: ViewModel() {

    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val codigoPostal = mutableStateOf("")
    val password = mutableStateOf("")
    val repetePassword = mutableStateOf("")

    val isFormValid = mutableStateOf(false)
    val dialogMessage = mutableStateOf("")
    val dialogTitle = mutableStateOf("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val allowedCodigosPostales = setOf( "03559", "03540", "03114", "03016", "03015", "03014", "03013", "03012", "03011",
        "03010", "03009", "03008", "03007", "03006", "03005", "03004", "03003", "03002", "03001")

    fun onCodigoPostalChange(newValue: String) {
        if (newValue.length <= 5 && newValue.all { it.isDigit() }) {
            codigoPostal.value = newValue
        }
    }

    fun onPasswordChange(newValue: String) {
        password.value = newValue
    }

    fun onRepetePasswordChange(newValue: String) {
        repetePassword.value = newValue
    }

    fun onEmailChange(newValue: String) {
        email.value = newValue
    }

    fun onUsernameChange(newValue: String) {
        username.value = newValue
    }

    fun validateForm() {
        val isEmailValid = android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
        val isCpValid = codigoPostal.value in allowedCodigosPostales
        val doPasswordsMatch = password.value == repetePassword.value && password.value.isNotEmpty()
        val areFieldsFilled = listOf(username.value, email.value, codigoPostal.value, password.value, repetePassword.value).all { it.isNotBlank() }

        isFormValid.value = isEmailValid && isCpValid && doPasswordsMatch && areFieldsFilled

        dialogMessage.value = when {
            !isEmailValid -> {
                dialogTitle.value = "Error"
                "Correo electrónico inválido."
            }
            !isCpValid -> {
                dialogTitle.value = "Error"
                "Codigo postal inválido."
            }
            !doPasswordsMatch -> {
                dialogTitle.value = "Error"
                "Las contraseñas no coinciden."
            }
            !areFieldsFilled -> {
                dialogTitle.value = "Error"
                "Tienes que rellenar todos los campos."
            }
            else -> ""
        }
    }

    fun registrarUser(){
        val requestData = mutableMapOf<String, String>()
        val hashedPassword = BCrypt.hashpw(password.value, BCrypt.gensalt())
        Log.d("Password", password.value)
        Log.d("Password HASHED", hashedPassword)
        requestData["username"] = username.value
        requestData["codigopostal"] = codigoPostal.value
        requestData["email"] = email.value
        requestData["codigoactiva"] = generaCodigo()
        requestData["activado"] = "false"
        requestData["password"] = hashedPassword

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.api.registrarUser(requestData)
                if (response.isSuccessful) {
                    response.body()?.let {
                        dialogTitle.value = "Exito"
                        dialogMessage.value = it["message"] ?: "Registro creado."
                    } ?: run {
                        dialogTitle.value = "Exito"
                        dialogMessage.value = "Registro correcto."
                    }

                    username.value = ""
                    password.value = ""
                    repetePassword.value = ""
                    codigoPostal.value = ""
                    email.value = ""

                } else {
                    val errorBody = response.errorBody()?.string()
                    dialogMessage.value = errorBody ?: "Error al registrar ususario. Código: ${response.code()}"
                }
            } catch (e: Exception) {
                dialogMessage.value = when (e) {
                    is SocketTimeoutException -> "Tiempo de espera agotado. Inténtalo de nuevo."
                    is ConnectException -> "No se pudo conectar al servidor."
                    is IOException -> "Error de red: ${e.message}"
                    else -> "Error inesperado: ${e.message}"
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

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