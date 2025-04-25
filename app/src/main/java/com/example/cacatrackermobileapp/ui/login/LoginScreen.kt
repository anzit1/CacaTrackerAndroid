package com.example.cacatrackermobileapp.ui.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.LoginViewModel

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = viewModel.checked.value,
                    onCheckedChange = { newChecKValue ->
                        viewModel.checked.value = newChecKValue
                    }
                )
                Text("Remember me")
            }
            TextButton(onClick = {}) {
                Text("Olvidé contraseña")
            }
        }

        Spacer(Modifier.height(itemSpacing * 3))
        // ** LOGIN **
        //
        ButtonCT(null, "Login", { onLoginClick() })

        Spacer(Modifier.height(itemSpacing * 3))
        // ** REGISTRAR **
        //
        ButtonCT(null, "Crear cuenta", onRegisterClick)

    }


}

@Preview(showBackground = true)
@Composable
fun PrevLoginScreen() {
    CacaTrackerMobileAppTheme {
        LoginScreen(LoginViewModel(), {}, {})
    }
}