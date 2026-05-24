package com.example.passwordmanager2.ui.screens

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager2.data.session.SessionManager
import kotlinx.coroutines.flow.first

@Composable
fun PinUnlockScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    val sessionManager =
        SessionManager(context)

    var pin by remember {
        mutableStateOf("")
    }

    var error by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF2F1E9)
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                Text(
                    text = "Введите PIN",
                    fontSize = 30.sp
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                OutlinedTextField(

                    value = pin,

                    onValueChange = {

                        if (it.length <= 4) {
                            pin = it
                        }
                    },

                    visualTransformation =
                        PasswordVisualTransformation(),

                    label = {
                        Text("PIN")
                    }
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                if (error.isNotEmpty()) {

                    Text(
                        text = error,
                        color = Color.Red
                    )
                }

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Button(

                    onClick = {

                        CoroutineScope(
                            Dispatchers.IO
                        ).launch {

                            val savedPin =
                                sessionManager
                                    .currentPin
                                    .first()

                            withContext(
                                Dispatchers.Main
                            ) {

                                if (savedPin == pin) {

                                    navController?.navigate("home")

                                } else {

                                    error = "Неверный PIN"
                                }
                            }
                        }
                    },

                    enabled = pin.length == 4
                ) {

                    Text("Войти")
                }
            }
        }
    }
}