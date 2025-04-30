package com.example.cacatrackermobileapp.ui.tusincidencias

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.R
import com.example.cacatrackermobileapp.data.models.Incidencias
import com.example.cacatrackermobileapp.ui.components.BotInfoBar
import com.example.cacatrackermobileapp.ui.components.ButtonPQ
import com.example.cacatrackermobileapp.ui.components.TopInfoBar
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.TusIncViewModel

@Composable
fun TusIncidenciasScreen(
    viewModel: TusIncViewModel = viewModel(),
    onVolverClick: () -> Unit
) {
    viewModel.check()
    val incidencias by viewModel.incidencias.collectAsState()
    val loading by viewModel.loading.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.cargaIncidencias()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6E6E6))
            .systemBarsPadding()
    ) {
        TopInfoBar("Tus incidencias")

        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000))
                    .align(Alignment.Center)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 56.dp,
                    bottom = 72.dp
                ),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(incidencias) { incidencia ->
                IncidenciaItem(incidencia) {
                    incidencia.id?.let {
                        viewModel.deleteIncidencia(it) {
                            // Optionally show snackbar or toast
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            BotInfoBar(
                "Volver",
                onVolverClick
            )
        }
    }
}


@Composable
fun IncidenciaItem(incidencia: Incidencias, onDelete: () -> Unit) {

    val context = LocalContext.current
    val defaultBitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.logo).asImageBitmap()


    val imageBitmap = try {
        incidencia.foto?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size).asImageBitmap()
        }
    } catch (e: Exception) {
        null
    } ?: defaultBitmap

    Log.d("TusIncidencias", "foto size: ${incidencia.foto?.size}")

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(Color(0xFFF9F9F9))
                .padding(12.dp)
        ) {
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text("Dirección: ${incidencia.direccion}")
                Text("Código Postal: ${incidencia.codigopostal}")
                Text(
                    "Nombre artístico: ${incidencia.nombreartistico}",
                    fontWeight = FontWeight.Bold
                )
                Row(modifier = Modifier.align(Alignment.End)) {
                    ButtonPQ(95, "Borrar", { onDelete() })
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TusIncidenciasPreview() {
    CacaTrackerMobileAppTheme {
        TusIncidenciasScreen(
            TusIncViewModel(),
            onVolverClick = {}
        )
    }
}