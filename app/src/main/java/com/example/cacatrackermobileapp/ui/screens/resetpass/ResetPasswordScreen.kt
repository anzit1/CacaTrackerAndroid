package com.example.cacatrackermobileapp.ui.screens.resetpass

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cacatrackermobileapp.viewmodels.ResetPasswordViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.R
import com.example.cacatrackermobileapp.ui.components.BotInfoBar
import com.example.cacatrackermobileapp.ui.components.ButtonCT
import com.example.cacatrackermobileapp.ui.components.HeaderText
import com.example.cacatrackermobileapp.ui.components.TopInfoBar
import com.example.cacatrackermobileapp.ui.screens.login.defaultPadding

@Composable
fun ResetPasswordScreen(
    onVolverClick: () -> Unit,
    onRestablecerClick: () -> Unit,
    viewModel: ResetPasswordViewModel = viewModel()
) {
    val successMsg = viewModel.successMessage.value
    val errorMsg = viewModel.errorMessage.value

    var showAlertDialog by remember { mutableStateOf(false) }
    var alertDialogMessage by remember { mutableStateOf("") }

    LaunchedEffect(successMsg) {
        if (successMsg != null && successMsg.contains("Contraseña restablecida con éxito")) {
            alertDialogMessage = successMsg
            showAlertDialog = true
            onRestablecerClick()
        }
    }

    LaunchedEffect(errorMsg) {
        if (errorMsg != null) {
            alertDialogMessage = errorMsg
            showAlertDialog = true
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf7f7f7))
            .systemBarsPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopInfoBar("Restablecer contraseña")

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            HeaderText(
                text = null,
                image = painterResource(id = R.drawable.logo),
                190,
                modifier = Modifier
                    .padding(vertical = defaultPadding)
                    .align(alignment = Alignment.Start)
            )

            OutlinedTextField(
                value = viewModel.email.value,
                onValueChange = { viewModel.email.value = it },
                placeholder = { Text("Correo Electrónico") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            OutlinedTextField(
                value = viewModel.codigoReset.value,
                onValueChange = { viewModel.codigoReset.value = it },
                placeholder = { Text("Código en tu email") },
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            OutlinedTextField(
                value = viewModel.password.value,
                onValueChange = { viewModel.password.value = it },
                placeholder = { Text("Nueva contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            OutlinedTextField(
                value = viewModel.repitePassword.value,
                onValueChange = { viewModel.repitePassword.value = it },
                placeholder = { Text("Repetir contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(6.dp))
            ButtonCT(180, 70, "Restablecer", {
                Log.d("Restablecer","restablancendo contraseña....")
                viewModel.resetPassword()
            })
            Spacer(modifier = Modifier.weight(1f))

            BotInfoBar("Volver", onVolverClick)
        }
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = { showAlertDialog = false },
            title = { Text("Resultado") },
            text = { Text(alertDialogMessage) },
            confirmButton = {
                Button(onClick = { showAlertDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ResetPSPreview() {
    ResetPasswordScreen({}, {}, ResetPasswordViewModel())
}