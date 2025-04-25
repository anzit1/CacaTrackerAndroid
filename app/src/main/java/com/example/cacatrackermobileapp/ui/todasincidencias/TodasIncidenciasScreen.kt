package com.example.cacatrackermobileapp.ui.todasincidencias

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.ui.components.BotInfoBar
import com.example.cacatrackermobileapp.ui.components.TopInfoBar
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.TodasIncViewModel

@Composable
fun TodasIncidenciasScreen(
    viewModel: TodasIncViewModel = viewModel(),
    onVolverClick: () -> Unit
    ) {

    val incidencias = emptyList<String>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6E6E6))
            .systemBarsPadding()
    ) {

        TopInfoBar("Todas incidencias", "manolo")

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 20.dp)
        ) {
            items(incidencias) { incidencia ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Text(
                        text = incidencia,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        BotInfoBar("Volver", onVolverClick)
    }
}


@Preview(showBackground = true)
@Composable
fun TodasIncidenciasPreview() {
    CacaTrackerMobileAppTheme {
        TodasIncidenciasScreen(
            onVolverClick = {}

        )
    }
}