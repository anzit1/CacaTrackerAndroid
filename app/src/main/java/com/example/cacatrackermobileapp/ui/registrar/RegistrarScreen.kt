package com.example.cacatrackermobileapp.ui.registrar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.R
import com.example.cacatrackermobileapp.ui.components.HeaderText
import com.example.cacatrackermobileapp.ui.components.LoginTextField
import com.example.cacatrackermobileapp.ui.login.defaultPadding
import com.example.cacatrackermobileapp.ui.login.itemSpacing
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme

import com.example.cacatrackermobileapp.viewmodels.RegistrarViewModel

@Composable
fun RegistrarScreen(
    viewModel: RegistrarViewModel = viewModel(),
    onBackToLogin: () -> Unit = {}
) {

    val dialogMessage by viewModel.dialogMessage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(
            text = null,
            image = painterResource(id = R.drawable.logo),
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )

        LoginTextField(
            value = viewModel.username.value,
            onValueChange = {
                viewModel.username.value = it
            },
            labelText = "Username",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = viewModel.email.value,
            onValueChange = {
                viewModel.email.value = it
            },
            labelText = "Email",
            leadingIcon = Icons.Default.Email,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = viewModel.codigoPostal.value,
            onValueChange = { newValue ->
                if (newValue.length <= 5 && newValue.all { it.isDigit() }) {
                    viewModel.codigoPostal.value = newValue
                }
            },
            labelText = "Codigo Postal",
            leadingIcon = Icons.Default.Home,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = viewModel.password.value,
            onValueChange = {
                viewModel.password.value = it
            },
            labelText = "Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = viewModel.repetePassword.value,
            onValueChange = {
                viewModel.repetePassword.value = it
            },
            labelText = "Repetir password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing * 2))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    viewModel.validateForm()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear cuenta")
            }
        }
        Button(
            onClick = onBackToLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
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
fun RegistrarPreview() {
    CacaTrackerMobileAppTheme {
        RegistrarScreen()
    }
}