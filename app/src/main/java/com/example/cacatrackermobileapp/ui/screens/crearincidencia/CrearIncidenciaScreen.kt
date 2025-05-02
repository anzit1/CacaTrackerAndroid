package com.example.cacatrackermobileapp.ui.screens.crearincidencia

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cacatrackermobileapp.ui.components.BotInfoBar
import com.example.cacatrackermobileapp.ui.components.ButtonCT
import com.example.cacatrackermobileapp.ui.components.TopInfoBar
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.cacatrackermobileapp.ui.components.ButtonPQImg
import com.example.cacatrackermobileapp.viewmodels.CrearIncViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@SuppressLint("ContextCastToActivity")
@Composable
fun CrearIncidenciaScreen(
    viewModel: CrearIncViewModel = viewModel(),
    onVolverClick: () -> Unit
) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val dialogMessage by viewModel.dialogMessage
    val selectedImageUri = viewModel.selectedImageUri.value
    val scope = rememberCoroutineScope()

    val isLoading by viewModel.isLoading

    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            try {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                viewModel.subirFoto(uri, context)
            } catch (e: Exception) {
                viewModel.dialogMessage.value =
                    "Error al acceder a la imagen: ${e.localizedMessage}"
            }
        }
    }

    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scope.launch {
                pickMediaLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        } else {
            viewModel.dialogMessage.value =
                "Permission denied. You can't select images without permission."
        }
    }


    // Function to handle image selection with permission check
    fun handleImageSelection() {
        val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Manifest.permission.READ_MEDIA_IMAGES
        } else {
            Manifest.permission.READ_EXTERNAL_STORAGE
        }

        when {
            ContextCompat.checkSelfPermission(
                context,
                permissionToRequest
            ) == PackageManager.PERMISSION_GRANTED -> {
                scope.launch {
                    pickMediaLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permissionToRequest
            ) -> {
                // Show rationale dialog
                scope.launch {
                    viewModel.dialogMessage.value =
                        "The app needs permission to access your photos to select images. " +
                                "Please grant the permission in the next dialog."
                    // Request permission after user acknowledges the rationale
                    delay(1000) // Small delay to ensure dialog is shown
                    permissionLauncher.launch(permissionToRequest)
                }
            }

            else -> {
                permissionLauncher.launch(permissionToRequest)
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .background(Color(0xFFD1D1D1))
        ) {

            TopInfoBar("Crear incidencia")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (selectedImageUri != null) {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier
                                .size(155.dp)
                                .background(Color.White)
                        )
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(selectedImageUri),
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier
                                .size(155.dp)
                                .background(Color.White)
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                    ButtonPQImg(
                        200,
                        "Seleccionar Foto",
                        { handleImageSelection() },
                        Icons.Default.Search
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .background(Color(0xFFD1D1D1))
                        .padding(16.dp)
                        .weight(2f)
                ) {
                    Text(
                        text = "Nombre Artístico",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    TextField(
                        value = viewModel.nombreArtInput.value,
                        onValueChange = viewModel::onNombreArtisticoChange,
                        placeholder = { Text("Escribe un nombre a la pieza") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Direccion",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Black)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        TextField(
                            value = viewModel.direccionInput.value,
                            onValueChange = viewModel::onDireccionChange,
                            placeholder = { Text("Escribe la direccion") },
                            modifier = Modifier.weight(2f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = viewModel.codigoPostalInput.value,
                            onValueChange = viewModel::onCodigoPostalChange,
                            placeholder = { Text("00000") },
                            enabled = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (viewModel.filteredAddresses.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .border(1.dp, Color.Gray)
                        ) {
                            items(viewModel.filteredAddresses) { address ->
                                Text(
                                    text = address,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            viewModel.onSuggestionSelected(address)
                                        }
                                        .padding(8.dp)
                                )
                            }
                        }
                    }


                    Spacer(modifier = Modifier.height(24.dp))
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        ButtonCT(170, null, "Crear", { viewModel.crearIncidencia(context) })
                    }
                }
            }
            BotInfoBar("Volver", onVolverClick)
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x88000000)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (dialogMessage.isNotEmpty()) {
            AlertDialog(
                onDismissRequest = {},
                title = { viewModel.dialogTitle.value },
                text = { Text(dialogMessage) },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.dialogMessage.value = ""
                        }
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Composable
fun SelectPhotoButton(viewModel: CrearIncViewModel) {
    val context = LocalContext.current

    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.subirFoto(uri, context)
        }
    }

    ButtonPQImg(200, "Seleccionar Foto", {
        pickMediaLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }, Icons.Default.Search)
}


@Preview(showBackground = true)
@Composable
fun CrearIncidenciaPreview() {
    CacaTrackerMobileAppTheme {
        CrearIncidenciaScreen(CrearIncViewModel(), {})
    }
}