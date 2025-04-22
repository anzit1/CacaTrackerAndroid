package com.example.cacatrackermobileapp.ui.mainuser

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
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.R
import com.example.cacatrackermobileapp.ui.components.ButtonCT
import com.example.cacatrackermobileapp.ui.components.HeaderText
import com.example.cacatrackermobileapp.ui.login.defaultPadding
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

    Box(modifier = Modifier.fillMaxSize())
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        )
        {
            Text(text = " User: nombre123 ")
        }

        Box {
            HeaderText(
                text = null,
                image = painterResource(id = R.drawable.logo),
                modifier = Modifier
                    .padding(vertical = defaultPadding)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            ButtonCT(null, "Crear incidencia", onCrearIncidenciaClick)
            Spacer(Modifier.height(itemSpacing))
            ButtonCT(null, "Tus incidencias", onTusIncidenciasClick)
            Spacer(Modifier.height(itemSpacing))
            ButtonCT(null, "Todas incidencias", onTodasIncidenciasClick)
            Spacer(Modifier.height(itemSpacing))
            ButtonCT(null, "Ver estadisticas", onEstadisticasClick)
            Spacer(Modifier.height(itemSpacing))
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
        )
        {
            ButtonCT(130, "Logout", onLogOutClick)
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
