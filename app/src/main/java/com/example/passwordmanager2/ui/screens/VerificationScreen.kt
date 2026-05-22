package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanager2.ui.components.PrimaryButton

@Composable
fun VerificationScreen(navController: NavController? = null) {

    var code1 by remember { mutableStateOf("") }
    var code2 by remember { mutableStateOf("") }
    var code3 by remember { mutableStateOf("") }
    var code4 by remember { mutableStateOf("") }

    var isError by remember {
        mutableStateOf(false)
    }

    val fullCode =
        code1 + code2 + code3 + code4

    val isCodeValid = fullCode.length == 4

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
                    .padding(20.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Введите код\nподтверждения",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A4A4A)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    VerificationField(
                        value = code1,
                        onValueChange = {
                            code1 = it.take(1)
                        },
                        isError = isError
                    )

                    VerificationField(
                        value = code2,
                        onValueChange = {
                            code2 = it.take(1)
                        },
                        isError = isError
                    )

                    VerificationField(
                        value = code3,
                        onValueChange = {
                            code3 = it.take(1)
                        },
                        isError = isError
                    )

                    VerificationField(
                        value = code4,
                        onValueChange = {
                            code4 = it.take(1)
                        },
                        isError = isError
                    )
                }

                if (isError) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Неверный код",
                        color = Color.Red
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                PrimaryButton(
                    text = "Войти",
                    enabled = isCodeValid,

                    onClick = {

                        if (fullCode == "7777") {

                            isError = false

                            navController?.navigate("home")

                        } else {

                            isError = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun VerificationField(
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean
) {

    OutlinedTextField(
        value = value,

        onValueChange = onValueChange,

        modifier = Modifier
            .width(70.dp)
            .height(70.dp),

        singleLine = true,

        shape = RoundedCornerShape(14.dp),

        isError = isError
    )
}

@Preview(
    showBackground = true
)
@Composable
fun VerificationScreenPreview() {

    VerificationScreen()
}