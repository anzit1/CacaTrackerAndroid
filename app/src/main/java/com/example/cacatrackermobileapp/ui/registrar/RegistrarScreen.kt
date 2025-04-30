package com.example.cacatrackermobileapp.ui.registrar

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
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cacatrackermobileapp.R
import com.example.cacatrackermobileapp.ui.components.ButtonCT
import com.example.cacatrackermobileapp.ui.components.HeaderText
import com.example.cacatrackermobileapp.ui.components.LoginTextField
import com.example.cacatrackermobileapp.ui.login.defaultPadding
import com.example.cacatrackermobileapp.ui.login.itemSpacing
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.RegistrarViewModel

@Composable
fun RegistrarScreen(
    viewModel: RegistrarViewModel = viewModel(),
    onBackToLogin: () -> Unit,
    onCrearCuentaClick: () -> Unit
) {

    val dialogMessage by viewModel.dialogMessage
    val isFormValid by viewModel.isFormValid
    val loading by viewModel.isLoading.collectAsState()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFf7f7f7))
            .systemBarsPadding()
    ) {
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x80000000))
                    .align(Alignment.CenterHorizontally)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            HeaderText(
                text = null,
                image = painterResource(id = R.drawable.logo),
                null,
                modifier = Modifier
                    .padding(vertical = defaultPadding)
                    .align(alignment = Alignment.Center)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(defaultPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LoginTextField(
                value = viewModel.username.value,
                onValueChange = viewModel::onUsernameChange,
                labelText = "Username",
                leadingIcon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = viewModel.email.value,
                onValueChange = viewModel::onEmailChange,
                labelText = "Email",
                leadingIcon = Icons.Default.Email,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = viewModel.codigoPostal.value,
                onValueChange = viewModel::onCodigoPostalChange,
                labelText = "Codigo Postal",
                leadingIcon = Icons.Default.Home,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = viewModel.password.value,
                onValueChange = viewModel::onPasswordChange,
                labelText = "Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(Modifier.height(itemSpacing))
            LoginTextField(
                value = viewModel.repetePassword.value,
                onValueChange = viewModel::onRepetePasswordChange,
                labelText = "Repetir password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier.fillMaxWidth(),
                keyboardType = KeyboardType.Password,
                visualTransformation = PasswordVisualTransformation()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonCT(170, null,"Crear cuenta", onCrearCuentaClick) {
                    viewModel.validateForm()
                    if(isFormValid){
                        viewModel.registrarUser()
                    }
                }
                Spacer(Modifier.width(10.dp))
                ButtonCT(170, null,"Volver", onBackToLogin, null)
            }
        }
    }

    if (dialogMessage.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(viewModel.dialogTitle.value) },
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
        RegistrarScreen(RegistrarViewModel(), {}, {})
    }
}