package com.example.cacatrackermobileapp.ui.estadisticas

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.viewmodels.EstadisticasViewModel

@Composable
fun EstadisticaScreen(
    viewModel: EstadisticasViewModel = viewModel(),
    onVolverClick: () -> Unit,
    graphContent: @Composable () -> Unit = {}
) {
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
        TopInfoBar("Estadísticas", "manolo")

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
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                ButtonCT(null, "Código Postal", { })
                Spacer(modifier = Modifier.height(8.dp))
                ButtonCT(null, "Calles/Avenidas", { })
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
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                ButtonCT(null, "Código Postal", { })
                Spacer(modifier = Modifier.height(8.dp))
                ButtonCT(null, "Calles/Avenidas", { })
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            SimpleGraph()
        }

        BotInfoBar("Volver", onVolverClick)
    }
}

@Composable
fun SimpleGraph() {
    val barHeights = listOf(80, 120, 60, 150, 100) // Example values

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Ejemplo de gráfico", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            barHeights.forEach { height ->
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 4.dp)
                ) {
                    drawRect(
                        color = Color.Blue,
                        topLeft = Offset(0f, size.height - height),
                        size = androidx.compose.ui.geometry.Size(size.width, height.toFloat())
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EstadisticaPreview() {
    CacaTrackerMobileAppTheme {
        EstadisticaScreen(
            onVolverClick = {},
            graphContent = { SimpleGraph() })
    }
}
