package com.example.passwordmanager2.ui.screens

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager2.R

@Composable
fun WelcomeScreen(
    navController: NavController? = null
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1E9))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(140.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.ic_shield
                ),

                contentDescription = null,

                modifier = Modifier.size(240.dp)
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Text(
                text = "Password manager",

                fontSize = 38.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF3D3D3D)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "Храните пароли в безопасности",

                fontSize = 18.sp,

                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Button(

                onClick = {
                    navController?.navigate(
                        "register"
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF7C90AA)
                )
            ) {

                Text(
                    text = "Регистрация",
                    fontSize = 20.sp
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Button(

                onClick = {
                    navController?.navigate(
                        "login"
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp),

                shape = RoundedCornerShape(18.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD5D5D5)
                )
            ) {

                Text(
                    text = "Войти",
                    fontSize = 20.sp,
                    color = Color.White
                )
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {

    WelcomeScreen()
}