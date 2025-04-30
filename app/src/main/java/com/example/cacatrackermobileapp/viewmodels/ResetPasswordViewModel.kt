package com.example.cacatrackermobileapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cacatrackermobileapp.data.api.RetrofitClient
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt

class ResetPasswordViewModel : ViewModel() {
    val email = mutableStateOf("")
    val codigoReset = mutableStateOf("")
    val password = mutableStateOf("")
    val repitePassword = mutableStateOf("")
    val errorMessage = mutableStateOf<String?>(null)
    val successMessage = mutableStateOf<String?>(null)

    fun resetPassword() {
        if (password.value != repitePassword.value) {
            errorMessage.value = "Las contraseñas no coinciden"
            return
        }

        val hashedPassword = BCrypt.hashpw(password.value, BCrypt.gensalt())

        viewModelScope.launch {
            try {
                val requestData = mapOf(
                    "email" to email.value,
                    "codigoReset" to codigoReset.value,
                    "password" to hashedPassword
                )

                val response = RetrofitClient.api.resetPassUser(requestData)

                val body = response.body()

                Log.d("RESPONSE RESET PASS", body.toString())

                if (response.isSuccessful && body != null) {
                    when {
                        body.containsKey("EXITO") && body["EXITO"]?.contains("PASSRESETOK") == true -> {
                            successMessage.value = "Contraseña restablecida con éxito"
                        }

                        body.containsKey("ERROR") -> {
                            errorMessage.value = body["ERROR"] ?: "Error desconocido del servidor"
                        }

                        else -> {
                            errorMessage.value = "Respuesta inesperada del servidor"
                        }
                    }
                } else {
                    errorMessage.value =
                        response.errorBody()?.string() ?: "Error al restablecer la contraseña"
                }
            } catch (e: Exception) {
                errorMessage.value = "Error de conexión: ${e.message}"
            }
        }
    }
}