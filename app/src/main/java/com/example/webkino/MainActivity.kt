package com.example.webkino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation()
        }
    }
}

@Composable
fun Navigation() {
    // Navigation implementation in main level
    val navController = rememberNavController()
    // Set the first screen to display to be homeScreen
    NavHost( navController, startDestination = "homeScreen") {
        // All the screens here
        composable( "homeScreen"){ HomeScreen(navController)}
        composable( "moviesScreen"){ MoviesScreen(navController)}
        composable( "settingsScreen"){ SettingsScreen(navController)}
    }
}