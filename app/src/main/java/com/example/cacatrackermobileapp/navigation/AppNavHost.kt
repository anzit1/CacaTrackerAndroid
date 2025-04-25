package com.example.cacatrackermobileapp.navigation

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
import com.example.cacatrackermobileapp.ui.crearincidencia.CrearIncidenciaScreen
import com.example.cacatrackermobileapp.ui.login.LoginScreen
import com.example.cacatrackermobileapp.ui.mainuser.MainUserScreen
import com.example.cacatrackermobileapp.ui.registrar.RegistrarScreen
import com.example.cacatrackermobileapp.ui.tusincidencias.TusIncidenciasScreen
import com.example.cacatrackermobileapp.ui.todasincidencias.TodasIncidenciasScreen
import com.example.cacatrackermobileapp.ui.estadisticas.EstadisticaScreen
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
}


@Composable
fun AppNavHost(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Route.LoginScreen().name
    ) {

        composable(route = Route.EstadisticasScreen().name) {
            EstadisticaScreen(
                onVolverClick = {
                    navHostController.navigate(
                        Route.MainScreen().name
                    )
                }
            )
        }


        composable(route = Route.TusIncScreen().name) {
            TusIncidenciasScreen(
                onVolverClick = {
                    navHostController.navigate(
                        Route.MainScreen().name
                    )
                }
            )
        }

        composable(route = Route.TodasIncScreen().name) {
            TodasIncidenciasScreen(
                onVolverClick = {
                    navHostController.navigate(
                        Route.MainScreen().name
                    )
                }
            )
        }

        composable(route = Route.CrearIncScreen().name) {
            CrearIncidenciaScreen(
                onVolverClick = {
                    navHostController.navigate(
                        Route.MainScreen().name
                    )
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
                    navHostController.navigateToSingleTop(
                        Route.RegistrarScreen().name
                    )
                },
                onLoginClick = {
                    viewModel.login()
                    Log.d("Login", "Attempting login with username: ${viewModel.username.value}")
                }

            )
        }

        composable(route = Route.RegistrarScreen().name) {
            RegistrarScreen(
                onCrearCuentaClick = {},

                onBackToLogin = {
                    navHostController.navigateToSingleTop(
                        Route.LoginScreen().name
                    )
                }
            )
        }

        composable(route = Route.MainScreen().name) {
            val viewModel: MainUserViewModel = viewModel()
            MainUserScreen(
                viewModel = viewModel,
                onLogOutClick = {
                    viewModel.logout()
                    navHostController.navigate(
                        Route.LoginScreen().name
                    )
                },
                onTusIncidenciasClick = {
                    navHostController.navigate(
                        Route.TodasIncScreen().name
                    )
                },
                onTodasIncidenciasClick = {
                    navHostController.navigate(
                        Route.TodasIncScreen().name
                    )
                },
                onCrearIncidenciaClick = {
                    navHostController.navigate(
                        Route.CrearIncScreen().name
                    )
                },
                onEstadisticasClick = {
                    navHostController.navigate(
                        Route.EstadisticasScreen().name
                    )
                }
            )
        }

    }
}

fun NavController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
