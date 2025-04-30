package com.example.cacatrackermobileapp.ui.login

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.LoginViewModel

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onRegisterClick: () -> Unit,
    onResetPassClick: () -> Unit,
    onLoginClick: () -> Unit
) {

    val loginError by viewModel.loginError
    val loginSuccess by viewModel.loginSuccess
    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isLoading by viewModel.isLoading

    val showActivationDialog by viewModel.showActivationDialog
    if (showActivationDialog) {
        ActivationCodeDialog(
            code = viewModel.activationCode.value,
            onCodeChange = { viewModel.activationCode.value = it },
            onConfirm = {
                viewModel.activateAccount(
                    onSuccess = { Toast.makeText(context, "Cuenta activada. Intenta logearte.", Toast.LENGTH_LONG).show() },
                    onError = { error -> viewModel._loginError.value = error }
                )
            },
            onDismiss = { viewModel.showActivationDialog.value = false },
            isLoading = isLoading
        )
    }

    if (!loginError.isNullOrEmpty()) {
        AlertDialog(
            onDismissRequest = { viewModel._loginError.value = null },
            title = { Text(viewModel._loginTitle.value) },
            text = { Text(loginError ?: "Unknown error") },
            confirmButton = {
                Button(
                    onClick = { viewModel._loginError.value = null }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(defaultPadding)
            .background(Color(0xFFf7f7f7))
            .systemBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderText(
            text = null,
            image = painterResource(id = R.drawable.logo),
            null,
            modifier = Modifier
                .padding(vertical = defaultPadding)
                .align(alignment = Alignment.Start)
        )
        LoginTextField(
            value = viewModel.username.value,
            onValueChange = { viewModel.username.value = it },
            labelText = "Username",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(itemSpacing))
        LoginTextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.password.value = it },
            labelText = "Password",
            leadingIcon = Icons.Default.Lock,
            modifier = Modifier.fillMaxWidth(),
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(itemSpacing))

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { showForgotPasswordDialog = true },
                enabled = !isLoading
            ) {
                Text("Olvidé contraseña")
            }
        }

        Spacer(Modifier.height(itemSpacing * 3))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        } else {
            ButtonCT(null, null, "Login", {
                viewModel.login()
                if (loginSuccess) {
                    onLoginClick()
                }
            })
        }

        Spacer(Modifier.height(itemSpacing * 3))
        // ** REGISTRAR **
        //
        ButtonCT(null, null, "Crear cuenta", onRegisterClick)

    }

    if (showForgotPasswordDialog) {
        ForgotPasswordDialog(
            onDismiss = { showForgotPasswordDialog = false },
            onConfirm = { email ->
                viewModel.requestPasswordReset(email)
                onResetPassClick()
            }, isLoading = isLoading
        )
    }

    LaunchedEffect(viewModel.resetPasswordMessage.value) {
        viewModel.resetPasswordMessage.value?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            viewModel.resetPasswordMessage.value = null
        }
    }
}

@Composable
fun ForgotPasswordDialog(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    isLoading: Boolean
) {
    var email by remember { mutableStateOf("") }
    var isEmailValid by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = { Text("Recuperar Contraseña") },
        text = {
            Column {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Text("Ingrese su email para recibir el código de restablecimiento")
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            isEmailValid = it.contains("@") && it.contains(".")
                        },
                        label = { Text("Email") },
                        isError = !isEmailValid && email.isNotEmpty(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        enabled = !isLoading
                    )
                    if (!isEmailValid && email.isNotEmpty()) {
                        Text(
                            text = "Ingrese un email válido",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Button(
                    onClick = {
                        if (isEmailValid) {
                            onConfirm(email)
                        }
                    },
                    enabled = isEmailValid && email.isNotEmpty() && !isLoading
                ) {
                    Text("Enviar")
                }
            }
        },
        dismissButton = {
            if (!isLoading) {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        }
    )
}

@Composable
fun ActivationCodeDialog(
    code: String,
    onCodeChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    isLoading: Boolean
) {
    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = { Text("Activar Cuenta") },
        text = {
            Column {
                Text("Introduce el código de activación enviado a tu correo.")
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = code,
                    onValueChange = onCodeChange,
                    label = { Text("Código de Activación") },
                    enabled = !isLoading
                )
            }
        },
        confirmButton = {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Button(
                    onClick = onConfirm,
                    enabled = code.isNotBlank()
                ) {
                    Text("Activar")
                }
            }
        },
        dismissButton = {
            if (!isLoading) {
                TextButton(onClick = onDismiss) {
                    Text("Cancelar")
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PrevLoginScreen() {
    CacaTrackerMobileAppTheme {
        LoginScreen(LoginViewModel(), {}, {}, {})
    }
}