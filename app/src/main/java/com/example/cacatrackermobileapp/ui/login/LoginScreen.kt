package com.example.cacatrackermobileapp.ui.login

import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cacatrackermobileapp.R
import com.example.cacatrackermobileapp.ui.components.HeaderText
import com.example.cacatrackermobileapp.ui.components.LoginTextField
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme
import com.example.cacatrackermobileapp.viewmodels.LoginViewModel

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel(),
    onRegisterClick: () -> Unit = {}
) {

    LaunchedEffect(viewModel.isLoginSuccessful) {
        if (viewModel.isLoginSuccessful.value) {
            navController.navigate("mainscreen") {
                popUpTo("login") { inclusive = true } // Ensure login screen is removed from back stack
            }
        }
    }


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
        Button(onClick = {
            viewModel.login()
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Login")
        }

        Button(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear cuenta")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrevLoginScreen() {
    CacaTrackerMobileAppTheme {
        LoginScreen(navController = rememberNavController())
    }
}