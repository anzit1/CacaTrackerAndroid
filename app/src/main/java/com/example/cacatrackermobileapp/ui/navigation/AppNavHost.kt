package com.example.cacatrackermobileapp.ui.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cacatrackermobileapp.ui.screens.crearincidencia.CrearIncidenciaScreen
import com.example.cacatrackermobileapp.ui.screens.login.LoginScreen
import com.example.cacatrackermobileapp.ui.screens.mainuser.MainUserScreen
import com.example.cacatrackermobileapp.ui.screens.registrar.RegistrarScreen
import com.example.cacatrackermobileapp.ui.screens.tusincidencias.TusIncidenciasScreen
import com.example.cacatrackermobileapp.ui.screens.todasincidencias.TodasIncidenciasScreen
import com.example.cacatrackermobileapp.ui.screens.estadisticas.EstadisticaScreen
import com.example.cacatrackermobileapp.ui.screens.resetpass.ResetPasswordScreen
import com.example.cacatrackermobileapp.viewmodels.LoginViewModel
import com.example.cacatrackermobileapp.viewmodels.MainUserViewModel

sealed class Route {
    data class LoginScreen(val name: String = "Login") : Route()
    data class RegistrarScreen(val name: String = "Registrar") : Route()
    data class MainScreen(val name: String = "Main") : Route()
    data class CrearIncScreen(val name: String = "CrearInc") : Route()
    data class TusIncScreen(val name: String = "TusInc") : Route()
    data class TodasIncScreen(val name: String = "TodasInc") : Route()
    data class EstadisticasScreen(val name: String = "Estadisticas") : Route()
    data class ResetPasswordScreen(val name: String = "ResetPassword") : Route()
}

@Composable
fun AppNavHost(navHostController: NavHostController) {

    fun NavHostController.navigateToMain() {
        navigate(Route.MainScreen().name) {
            popUpTo(graph.findStartDestination().id) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = Route.LoginScreen().name
    ) {

        composable(route = Route.ResetPasswordScreen().name) {
            ResetPasswordScreen(
                onVolverClick = {
                    navHostController.popBackStack()
                },
                onRestablecerClick = {
                    navHostController.navigate(Route.LoginScreen().name)
                }
            )
        }

        composable(route = Route.EstadisticasScreen().name) {
            EstadisticaScreen(
                onVolverClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(route = Route.TusIncScreen().name) {
            TusIncidenciasScreen(
                onVolverClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(route = Route.TodasIncScreen().name) {
            TodasIncidenciasScreen(
                onVolverClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(route = Route.CrearIncScreen().name) {
            CrearIncidenciaScreen(
                onVolverClick = {
                    navHostController.popBackStack()
                }
            )
        }

        composable(route = Route.LoginScreen().name) {
            val viewModel: LoginViewModel = viewModel()
            val loginSuccess by viewModel.loginSuccess
            val loginError by viewModel.loginError

            if (loginSuccess) {
                LaunchedEffect(Unit) {
                    navHostController.navigate(Route.MainScreen().name)
                }
            }

            LoginScreen(
                onRegisterClick = {
                    navHostController.navigateToSingleTop(Route.RegistrarScreen().name)
                },
                onLoginClick = {
                    viewModel.login()
                    Log.d("Login", "Attempting login with username: ${viewModel.username.value}")
                },
                onResetPassClick = {
                    navHostController.navigate(Route.ResetPasswordScreen().name)
                }
            )
        }

        composable(route = Route.RegistrarScreen().name) {
            RegistrarScreen(
                onCrearCuentaClick = {},
                onBackToLogin = {
                    navHostController.navigateToSingleTop(Route.LoginScreen().name)
                }
            )
        }

        composable(route = Route.MainScreen().name) {
            val viewModel: MainUserViewModel = viewModel()
            MainUserScreen(
                onLogOutClick = {
                    viewModel.logout()
                    navHostController.navigate(Route.LoginScreen().name){
                        popUpTo(0) { inclusive = true }
                    }
                },
                onTusIncidenciasClick = {
                    navHostController.navigate(Route.TusIncScreen().name)
                },
                onTodasIncidenciasClick = {
                    navHostController.navigate(Route.TodasIncScreen().name)
                },
                onCrearIncidenciaClick = {
                    navHostController.navigate(Route.CrearIncScreen().name)
                },
                onEstadisticasClick = {
                    navHostController.navigate(Route.EstadisticasScreen().name)
                }
            )
        }
    }
}

fun NavController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}