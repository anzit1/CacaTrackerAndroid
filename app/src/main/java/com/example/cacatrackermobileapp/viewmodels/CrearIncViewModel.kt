package com.example.cacatrackermobileapp.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
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
import com.example.cacatrackermobileapp.data.repository.DireccionesRepositoryImpl
import com.example.cacatrackermobileapp.domain.repository.DireccionesRepository
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException

class CrearIncViewModel() : ViewModel() {

    val username = UserSession.username
    val direccionInput = mutableStateOf("")
    val codigoPostalInput = mutableStateOf("")
    val nombreArtInput = mutableStateOf("")
    val selectedImageUri = mutableStateOf<Uri?>(null)
    val dialogMessage = mutableStateOf("")
    val dialogTitle = mutableStateOf("Error")
    val isLoading = mutableStateOf(false)

    private val _listaDeCalles = mutableStateListOf<Direccion>()
    val filteredAddresses = mutableStateListOf<String>()

    init {
        loadAddresses()
    }

    fun createImageUri(context: Context): Uri {
        val contentValues = android.content.ContentValues().apply {
            put(android.provider.MediaStore.Images.Media.DISPLAY_NAME, "captured_image_${System.currentTimeMillis()}.jpg")
            put(android.provider.MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        return context.contentResolver.insert(
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )!!
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

        selectedImageUri.value?.let { uri ->
            val encodedImage = compressAndEncodeImage(uri, context)
            encodedImage?.let {
                requestData["imagenCodificada64"] = it
                Log.d("Base64EncodedImage", "Encoded Image:  ${it.substring(0, minOf(50, it.length))}")
            }
        }

        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = RetrofitClient.api.createIncidencia(requestData)
                if (response.isSuccessful) {
                    response.body()?.let {
                        dialogTitle.value = "Exito"
                        dialogMessage.value = it["message"] ?: "Incidencia creada con éxito."
                    } ?: run {
                        dialogTitle.value = "Exito"
                        dialogMessage.value = "Incidencia creada con éxito."
                    }

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

    private fun compressAndEncodeImage(uri: Uri, context: Context, maxBytes: Long = 10 * 1024 * 1024): String? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()

            val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 1024, 1024, true)
            val outputStream = ByteArrayOutputStream()
            var quality = 100
            var byteArray: ByteArray

            do {
                outputStream.reset()
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
                byteArray = outputStream.toByteArray()
                quality -= 5
            } while (byteArray.size > maxBytes && quality > 10)

            return Base64.encodeToString(byteArray, Base64.NO_WRAP)

        } catch (e: Exception) {
            Log.e("ImageCompress", "Error compressing image: ${e.message}")
            return null
        }
    }
}
