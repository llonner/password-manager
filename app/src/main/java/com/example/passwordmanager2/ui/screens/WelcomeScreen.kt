package com.example.passwordmanager2.ui.screens

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import com.example.passwordmanager2.R

@Composable
fun WelcomeScreen(
    navController: NavController? = null
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .align(Alignment.Center),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF2F1E9)
            ),

            shape = RoundedCornerShape(0.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 48.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(150.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_shield),
                    contentDescription = null,
                    modifier = Modifier.size(220.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Password manager",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A4A4A)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Храните пароли в безопасности",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(150.dp))

                Button(
                    onClick = {
                        navController?.navigate("register")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF73839A)
                    )
                ) {

                    Text(
                        text = "Регистрация"
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        navController?.navigate("login")
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    )
                ) {

                    Text(
                        text = "Войти"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {

    WelcomeScreen()
}