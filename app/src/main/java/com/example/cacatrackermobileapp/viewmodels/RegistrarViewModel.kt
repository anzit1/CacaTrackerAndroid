package com.example.cacatrackermobileapp.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class RegistrarViewModel: ViewModel() {

    val username = mutableStateOf("")
    val email = mutableStateOf("")
    val codigoPostal = mutableStateOf("")
    val password = mutableStateOf("")
    val repetePassword = mutableStateOf("")

    val isFormValid = mutableStateOf(false)
    val dialogMessage = mutableStateOf("")

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
            !isEmailValid -> "Correo electrónico inválido."
            !isCpValid -> "Codigo postal inválido."
            !doPasswordsMatch -> "Las contraseñas no coinciden."
            !areFieldsFilled -> "Tienes que rellenar todos los campos."
            else -> ""
        }
    }
}