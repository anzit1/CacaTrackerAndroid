package com.example.cacatrackermobileapp.viewmodels

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cacatrackermobileapp.data.api.RetrofitClient
import com.example.cacatrackermobileapp.data.models.Direccion
import com.example.cacatrackermobileapp.data.models.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class CrearIncViewModel : ViewModel() {

    val username = UserSession.username
    val direccionInput = mutableStateOf("")
    val codigoPostalInput = mutableStateOf("")
    val nombreArtInput = mutableStateOf("")
    val selectedImageUri = mutableStateOf<Uri?>(null)
    val dialogMessage = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    private val _listaDeCalles = mutableStateListOf<Direccion>()
    val filteredAddresses = mutableStateListOf<String>()

    init {
        loadAddresses()
    }

    private fun loadAddresses() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getAllDirecciones()
                if (response.isSuccessful) {
                    response.body()?.let { calles ->
                        _listaDeCalles.clear()
                        _listaDeCalles.addAll(calles)
                    }
                }
            } catch (e: Exception) {
                dialogMessage.value = "Error al cargar direcciones: ${e.message}"
            }
        }
    }

    fun onDireccionChange(newValue: String) {
        direccionInput.value = newValue
        updateSuggestions(newValue)
    }

    private fun updateSuggestions(input: String) {
        if (input.isEmpty()) {
            filteredAddresses.clear()
            return
        }

        val lowerInput = input.lowercase()
        val matches = _listaDeCalles.filter {
            it.direccion!!.lowercase().contains(lowerInput)
        }.map {
            "${it.direccion} (${it.codigoPostal})"
        }
        filteredAddresses.clear()
        filteredAddresses.addAll(matches)
    }

    fun onSuggestionSelected(suggestion: String) {
        val parts = suggestion.split(" (")
        val direccion = parts[0]
        val codigoPostal = parts[1].replace(")", "")

        direccionInput.value = direccion
        codigoPostalInput.value = codigoPostal
        filteredAddresses.clear()
    }


    fun subirFoto(uri: Uri?, context: Context) {
        selectedImageUri.value = uri
        if (uri != null) {
            val inputStream = context.contentResolver.openInputStream(uri)
            val byteArray = inputStream?.use { it.readBytes() }

        }
    }

    fun onNombreArtisticoChange(newValue: String) {
        nombreArtInput.value = newValue
    }

    fun onCodigoPostalChange(newValue: String) {
        codigoPostalInput.value = newValue
    }

    fun encodeImageToBase64(uri: Uri, context: Context): String? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val byteArray = inputStream?.use { it.readBytes() }
        val encodedImage = byteArray?.let { Base64.encodeToString(it, Base64.NO_WRAP) }
        return encodedImage
    }

    fun crearIncidencia(context: Context) {
        if (nombreArtInput.value.isBlank() ||
            direccionInput.value.isBlank() ||
            codigoPostalInput.value.isBlank() ||
            selectedImageUri.value == null
        ) {
            dialogMessage.value = "Todos los campos son obligatorios."
            return
        }


        val requestData = mutableMapOf<String, String>()
        requestData["username"] = username ?: ""
        requestData["direccion"] = direccionInput.value
        requestData["nombreArtistico"] = nombreArtInput.value
        requestData["codigoPostal"] = codigoPostalInput.value

        // Encode image if available
         selectedImageUri.value?.let { uri ->
             val encodedImage = encodeImageToBase64(uri, context)
             encodedImage?.let {
                 requestData["imagenCodificada64"] = it
                 Log.d("Base64EncodedImage", "Encoded Image:  ${encodedImage.substring(0, minOf(50, encodedImage.length))}")
             }
         }
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.createIncidencia(requestData)
                if (response.isSuccessful) {
                    response.body()?.let {
                        dialogMessage.value = it["message"] ?: "Incidencia creada con éxito."
                    } ?: run {
                        dialogMessage.value = "Incidencia creada con éxito."
                    }

                    // Clear form after successful submission
                    nombreArtInput.value = ""
                    direccionInput.value = ""
                    codigoPostalInput.value = ""
                    selectedImageUri.value = null
                } else {
                    val errorBody = response.errorBody()?.string()
                    dialogMessage.value = errorBody ?: "Error al crear la incidencia. Código: ${response.code()}"
                }
            } catch (e: Exception) {
                dialogMessage.value = when (e) {
                    is SocketTimeoutException -> "Tiempo de espera agotado. Inténtalo de nuevo."
                    is ConnectException -> "No se pudo conectar al servidor."
                    is IOException -> "Error de red: ${e.message}"
                    else -> "Error inesperado: ${e.message}"
                }
            } finally {
                isLoading.value = false
            }
        }
    }
}
