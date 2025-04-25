package com.example.cacatrackermobileapp.ui.crearincidencia

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
import androidx.compose.runtime.getValue
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.example.cacatrackermobileapp.viewmodels.CrearIncViewModel

@Composable
fun CrearIncidenciaScreen(
    viewModel: CrearIncViewModel = viewModel(),
    onVolverClick: () -> Unit
) {
    val context = LocalContext.current
    val dialogMessage by viewModel.dialogMessage
    val selectedImageUri = viewModel.selectedImageUri.value
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.subirFoto(uri,context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(Color(0xFFD1D1D1))
    ) {

        TopInfoBar("Crear incidencia", "manolo")

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
                ButtonCT(150, "Subir foto", {
                    imagePickerLauncher.launch("image/*")
                })
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
                        enabled = false,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    //mal de momento
                    ButtonCT(170, "Crear", onVolverClick, null)
                    //mal de momento
                }

            }
        }
        BotInfoBar("Volver", onVolverClick)
    }



    if (dialogMessage.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Error") },
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

@Preview(showBackground = true)
@Composable
fun CrearIncidenciaPreview() {
    CacaTrackerMobileAppTheme {
        CrearIncidenciaScreen(CrearIncViewModel(), {})
    }
}