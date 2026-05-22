package com.example.passwordmanager2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.passwordmanager2.ui.screens.*


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {
            WelcomeScreen(navController)
        }

        composable("register") {
            RegisterScreen(navController)
        }

        composable("login") {
            LoginScreen(navController)
        }

        composable("verification") {
            VerificationScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }

        composable("add_password") {
            AddPasswordScreen()
        }
    }

}