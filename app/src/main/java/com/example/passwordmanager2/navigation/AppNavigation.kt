package com.example.passwordmanager2.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.passwordmanager2.data.session.SessionManager
import com.example.passwordmanager2.ui.screens.*
import kotlinx.coroutines.flow.first

@Composable
fun AppNavigation() {

    val navController =
        rememberNavController()

    val context =
        LocalContext.current

    var startDestination by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(Unit) {

        val sessionManager =
            SessionManager(context)

        val savedPhone =
            sessionManager
                .currentUserPhone
                .first()

        val savedPin =
            sessionManager
                .currentPin
                .first()

        startDestination =

            if (savedPhone != null) {

                if (!savedPin.isNullOrEmpty()) {

                    "pin_unlock"

                } else {

                    "home"
                }

            } else {

                "welcome"
            }
    }

    if (startDestination != null) {

        NavHost(

            navController = navController,

            startDestination =
                startDestination!!
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

            composable(
                route = "verification/{phone}"
            ) {

                val phone =
                    it.arguments
                        ?.getString("phone")
                        ?: ""

                VerificationScreen(
                    navController = navController,
                    phone = phone
                )
            }

            composable("home") {

                HomeScreen(navController)
            }

            composable("add_password") {

                AddPasswordScreen(navController)
            }

            composable("profile") {

                ProfileScreen(navController)
            }

            composable(
                route =
                    "password_details/{passwordId}"
            ) {

                val passwordId =
                    it.arguments
                        ?.getString("passwordId")
                        ?.toIntOrNull() ?: 0

                PasswordDetailsScreen(
                    navController = navController,
                    passwordId = passwordId
                )
            }

            composable(
                route =
                    "edit_password/{passwordId}"
            ) {

                val passwordId =
                    it.arguments
                        ?.getString("passwordId")
                        ?.toIntOrNull() ?: 0

                EditPasswordScreen(
                    navController = navController,
                    passwordId = passwordId
                )
            }

            composable("useful") {

                UsefulScreen(navController)
            }

            composable("breach_check") {

                BreachCheckScreen(navController)
            }

            composable("anti_keylogger") {

                KeyloggerProtectionScreen(
                    navController
                )
            }

            composable("edit_profile") {

                EditProfileScreen(navController)
            }

            composable("security_center") {

                SecurityCenterScreen(
                    navController
                )
            }

            composable("pin_setup") {

                PinSetupScreen(navController)
            }

            composable("pin_unlock") {

                PinUnlockScreen(navController)
            }

            composable("settings") {

                SettingsScreen(navController)
            }
        }
    }
}