package com.example.cacatrackermobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.cacatrackermobileapp.navigation.AppNavHost
import com.example.cacatrackermobileapp.ui.theme.CacaTrackerMobileAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CacaTrackerMobileAppTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}