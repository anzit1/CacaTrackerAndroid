package com.example.cacatrackermobileapp.ui.mainuser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.R
import com.example.cacatrackermobileapp.data.models.UserSession
import com.example.cacatrackermobileapp.ui.components.BotInfoBar
import com.example.cacatrackermobileapp.ui.components.ButtonCT
import com.example.cacatrackermobileapp.ui.components.HeaderText
import com.example.cacatrackermobileapp.ui.components.TopInfoBar
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.MainUserViewModel

val itemSpacing = 30.dp

@Composable
fun MainUserScreen(
    viewModel: MainUserViewModel = viewModel(),
    onLogOutClick: () -> Unit,
    onCrearIncidenciaClick: () -> Unit,
    onEstadisticasClick: () -> Unit,
    onTusIncidenciasClick: () -> Unit,
    onTodasIncidenciasClick: () -> Unit,
) {

    Column(modifier = Modifier.fillMaxSize()
        .systemBarsPadding()) {

        TopInfoBar("Menu principal", UserSession.userName)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(Color(0xFFd6d6d6)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ButtonCT(null, "Crear incidencia", onCrearIncidenciaClick)
            Spacer(Modifier.height(itemSpacing))
            ButtonCT(null, "Tus incidencias", onTusIncidenciasClick)
            Spacer(Modifier.height(itemSpacing))
            ButtonCT(null, "Todas incidencias", onTodasIncidenciasClick)
            Spacer(Modifier.height(itemSpacing))
            ButtonCT(null, "Ver estadisticas", onEstadisticasClick)
        }

        BotInfoBar("Logout",onLogOutClick)
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainUserScreen() {
    CacaTrackerMobileAppTheme {
        MainUserScreen(MainUserViewModel(), {}, {}, {}, {}, {})
    }
}
