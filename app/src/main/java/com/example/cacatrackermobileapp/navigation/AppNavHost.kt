package com.example.cacatrackermobileapp.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cacatrackermobileapp.ui.login.LoginScreen
import com.example.cacatrackermobileapp.ui.mainuser.MainUserScreen
import com.example.cacatrackermobileapp.ui.registrar.RegistrarScreen


sealed class Route {
    data class LoginScreen(val name: String = "Login") : Route()
    data class RegistrarScreen(val name: String = "Registrar") : Route()
    data class MainScreen(val name: String = "Main") : Route()
    //data class LoginScreen(val name:String):Route()
    //data class LoginScreen(val name:String):Route()

}



@Composable
fun AppNavHost(navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Route.LoginScreen().name
    ) {

        composable(route = Route.LoginScreen().name) {
            LoginScreen(
                onRegisterClick = {
                    navHostController.navigateToSingleTop(
                        Route.RegistrarScreen().name
                    )
                },
                onLoginClick = {
                    navHostController.navigate(
                        Route.MainScreen().name
                    )
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
            MainUserScreen(
                onLogOutClick = {},
                onTusIncidenciasClick = {},
                onTodasIncidenciasClick = {},
                onCrearIncidenciaClick = {},
                onEstadisticasClick = {}
            )
        }

    }
}

fun NavController.navigateToSingleTop(route:String){
    navigate(route){
        popUpTo(graph.findStartDestination().id){
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
