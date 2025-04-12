package com.example.cacatrackermobileapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cacatrackermobileapp.ui.login.LoginScreen
import com.example.cacatrackermobileapp.ui.mainuser.MainUserScreen
import com.example.cacatrackermobileapp.ui.registrar.RegistrarScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MAINSCREEN = "mainscreen"
    const val CREARINCIDENCIA = "nuevaincidencia"
    const val TUSINCIDENCIAS = "misincidencias"
    const val TODASINCIDENCIAS = "todasincidencias"
    const val ESTADISTICAS = "estadisticas"
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                navController = navController,
                onRegisterClick = { navController.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.REGISTER) {
            RegistrarScreen(
                onBackToLogin = { navController.popBackStack() }
            )
        }
        composable(Routes.MAINSCREEN) {
            MainUserScreen(
                onBackToLogin = { navController.popBackStack() }
            )
        }
    }
}
