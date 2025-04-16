package com.example.cacatrackermobileapp.ui.mainuser

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.MainUserViewModel

@Composable
fun MainUserScreen(
    viewModel: MainUserViewModel = viewModel(),
    onLogOutClick: () -> Unit,
    onCrearIncidenciaClick: () -> Unit,
    onEstadisticasClick: () -> Unit,
    onTusIncidenciasClick: () -> Unit,
    onTodasIncidenciasClick: () -> Unit,
) {
    // top bar nombre de la pagina a la izquierda + nombre usuario a la derecha
    // 4 botones main page
    // 1 boton right corner para logout

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Main Screen")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewMainUserScreen() {
    CacaTrackerMobileAppTheme {
        MainUserScreen(MainUserViewModel(), {}, {}, {}, {}, {})
    }
}
