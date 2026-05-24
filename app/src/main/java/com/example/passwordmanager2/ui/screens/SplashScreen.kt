package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.passwordmanager2.data.session.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(
    navController: NavController
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {

        delay(1500)

        val sessionManager =
            SessionManager(context)

        val phone =
            sessionManager
                .currentUserPhone
                .first()

        val pinEnabled =
            sessionManager
                .isPinEnabled
                .first()

        when {

            phone == null -> {

                navController.navigate("welcome")
            }

            pinEnabled -> {

                navController.navigate(
                    "pin_unlock"
                )
            }

            else -> {

                navController.navigate("home")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E)),

        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator()
    }
}