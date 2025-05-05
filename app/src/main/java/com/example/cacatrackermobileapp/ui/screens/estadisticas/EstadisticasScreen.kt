package com.example.cacatrackermobileapp.ui.screens.estadisticas

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cacatrackermobileapp.ui.components.BotInfoBar
import com.example.cacatrackermobileapp.ui.components.ButtonCT
import com.example.cacatrackermobileapp.ui.components.TopInfoBar
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.focus.focusModifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.data.models.CodigoPostalCountDTO
import com.example.cacatrackermobileapp.data.models.DireccionCountDTO
import com.example.cacatrackermobileapp.data.models.UserSession
import com.example.cacatrackermobileapp.ui.components.ButtonPQ
import com.example.cacatrackermobileapp.utils.PieChart
import com.example.cacatrackermobileapp.viewmodels.EstadisticasViewModel

@Composable
fun EstadisticaScreen(
    viewModel: EstadisticasViewModel = viewModel(),
    onVolverClick: () -> Unit
) {
    val codigoPostalData by viewModel.codigoPostalData.collectAsState()
    val direccionData by viewModel.direccionData.collectAsState()
    Log.d("Estadisticas", "Codigo Postal Data: $codigoPostalData")
    //Controla tipo de grafico
    var selectedChart by remember { mutableStateOf("none") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFE6E6E6))
                )
            )
    ) {
        TopInfoBar("Estadísticas")

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFE6E6E6), Color(0xFFB2B1B1), Color(0xFFE6E6E6))
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "Tus incidencias según:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                ButtonPQ(null,  41,"Código Postal", {
                    UserSession.id?.let {
                        viewModel.fetchTop5CpByUser(it)
                        selectedChart = "user_cp"
                    }
                })
                Spacer(modifier = Modifier.height(8.dp))
                ButtonPQ(null, 41, "Direcciones", {
                    UserSession.id?.let {
                        viewModel.fetchTop5DirByUser(it)
                        selectedChart = "user_dir"
                    }
                })
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        Brush.verticalGradient(
                            listOf(Color(0xFFE6E6E6), Color(0xFFB2B1B1), Color(0xFFE6E6E6))
                        )
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = "Todas incidencias según:",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                ButtonPQ(null,41, "Código Postal", {
                    viewModel.fetchTop5CpGlobal()
                    selectedChart = "global_cp"
                })
                Spacer(modifier = Modifier.height(8.dp))
                ButtonPQ(null,41, "Direcciones", {
                    viewModel.fetchTop5DirGlobal()
                    selectedChart = "global_dir"
                })
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            when (selectedChart) {
                "user_cp", "global_cp" -> CodigoPostalGraph(codigoPostalData)
                "user_dir", "global_dir" -> DireccionGraph(direccionData)
                else ->
                    Row(Modifier.align(Alignment.Center)) {
                        Text("Seleccione una opción para ver el gráfico.")
                    }
            }
        }

        BotInfoBar("Volver", onVolverClick)
    }
}

@Composable
fun CodigoPostalGraph(data: List<CodigoPostalCountDTO>) {
    if (data.isEmpty()) {
        Text("No hay datos disponibles.")
        return
    }

    val pieData = data.associate { it.codigopostal to it.total }
    Log.d("Estadisticas", "Codigo Postal Data: $pieData")
    PieChart(
        data = pieData,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )

}

@Composable
fun DireccionGraph(data: List<DireccionCountDTO>) {
    if (data.isEmpty()) {
        Text("No hay datos disponibles.")
        return
    }

    val pieData = data.associate { it.direccion to it.total }
    Log.d("Estadisticas", "Direcciones Data: $pieData")
    PieChart(
        data = pieData,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun EstadisticaPreview() {
    CacaTrackerMobileAppTheme {
        EstadisticaScreen(
            onVolverClick = {}
        )
    }
}
