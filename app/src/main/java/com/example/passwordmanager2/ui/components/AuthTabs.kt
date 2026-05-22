package com.example.passwordmanager2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AuthTabs(
    isRegisterSelected: Boolean,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color(0xFFE9E7DD),
                RoundedCornerShape(12.dp)
            )
            .padding(4.dp)
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (isRegisterSelected)
                        Color.White
                    else
                        Color.Transparent,

                    RoundedCornerShape(10.dp)
                )
                .clickable {
                    onRegisterClick()
                }
                .padding(vertical = 12.dp),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Регистрация",
                color = if (isRegisterSelected)
                    Color(0xFF73839A)
                else
                    Color.DarkGray,

                fontWeight = FontWeight.Medium
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .background(
                    if (!isRegisterSelected)
                        Color.White
                    else
                        Color.Transparent,

                    RoundedCornerShape(10.dp)
                )
                .clickable {
                    onLoginClick()
                }
                .padding(vertical = 12.dp),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "Войти",
                color = if (!isRegisterSelected)
                    Color(0xFF73839A)
                else
                    Color.DarkGray,

                fontWeight = FontWeight.Medium
            )
        }
    }
}