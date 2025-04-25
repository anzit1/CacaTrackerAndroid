package com.example.cacatrackermobileapp.viewmodels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel

class CrearIncViewModel : ViewModel() {

    val direccionInput = mutableStateOf("")
    val codigoPostalInput = mutableStateOf("")
    val nombreArtInput = mutableStateOf("")
    val selectedImageUri = mutableStateOf<Uri?>(null)
    val dialogMessage = mutableStateOf("")

    fun subirFoto(uri: Uri?,context: Context) {
        selectedImageUri.value = uri
        if (uri != null) {
            val inputStream = context.contentResolver.openInputStream(uri)
            val byteArray = inputStream?.use { it.readBytes() }
            // Now you have byteArray ready to upload
        }
    }

    fun onNombreArtisticoChange(newValue: String) {
        nombreArtInput.value = newValue
    }

    fun onDireccionChange(newValue: String) {
        direccionInput.value = newValue
    }

    fun onCodigoPostalChange(newValue: String) {
        codigoPostalInput.value = newValue
    }

    fun crearIncidencia() {
        if (nombreArtInput.value == "" ||
            direccionInput.value == "" ||
            codigoPostalInput.value == ""){

        }

    }


}