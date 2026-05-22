package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager2.R
import com.example.passwordmanager2.ui.components.AuthTabs
import com.example.passwordmanager2.ui.components.AuthTextField
import com.example.passwordmanager2.ui.components.PrimaryButton

@Composable
fun LoginScreen(
    navController: NavController? = null
) {

    var phone by remember {
        mutableStateOf("")
    }

    val isFormValid = phone.isNotBlank()

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
                    .verticalScroll(rememberScrollState()),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Зарегистрируетесь\nили войдите в аккаунт",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A4A4A)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        AuthTabs(
                            isRegisterSelected = false,

                            onRegisterClick = {
                                navController?.navigate("register")
                            },

                            onLoginClick = {

                            }
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        AuthTextField(
                            value = phone,
                            label = "Номер телефона",
                            onValueChange = {
                                phone = it
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = "Запросить код",
                    enabled = isFormValid,

                    onClick = {
                        navController?.navigate("verification")
                    }
                )
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun LoginScreenPreview() {

    LoginScreen()
}