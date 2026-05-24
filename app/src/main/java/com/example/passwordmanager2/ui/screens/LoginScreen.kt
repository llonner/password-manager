package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager2.R
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.ui.components.AuthTabs
import com.example.passwordmanager2.ui.components.PrimaryButton
import com.example.passwordmanager2.utils.PhoneVisualTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    var phone by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var isRegisterSelected by remember {
        mutableStateOf(false)
    }

    val isFormValid = phone.length == 10

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1E9))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
                .verticalScroll(
                    rememberScrollState()
                ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(50.dp)
            )

            Image(
                painter = painterResource(
                    id = R.drawable.ic_lock
                ),

                contentDescription = null,

                modifier = Modifier.size(110.dp)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text =
                    "Зарегистрируйтесь\nили войдите в аккаунт",

                fontSize = 32.sp,

                fontWeight = FontWeight.Bold,

                lineHeight = 38.sp,

                color = Color(0xFF3F3F3F)
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Card(

                modifier = Modifier.fillMaxWidth(),

                colors = CardDefaults.cardColors(
                    containerColor =
                        Color(0xFFF2F1E9)
                ),

                shape = MaterialTheme.shapes.large
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    AuthTabs(

                        isRegisterSelected =
                            isRegisterSelected,

                        onRegisterClick = {
                            navController?.navigate(
                                "register"
                            )
                        },

                        onLoginClick = {
                            isRegisterSelected = false
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    OutlinedTextField(

                        value = phone,

                        onValueChange = {

                            val digits =
                                it.filter { char ->
                                    char.isDigit()
                                }

                            if (digits.length <= 10) {

                                phone = digits
                            }
                        },

                        label = {
                            Text("Номер телефона")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),

                        singleLine = true,

                        shape =
                            MaterialTheme.shapes.medium,

                        visualTransformation =
                            PhoneVisualTransformation(),

                        keyboardOptions =
                            KeyboardOptions(
                                keyboardType =
                                    KeyboardType.Number
                            )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(340.dp)
            )

            PrimaryButton(

                text = "Запросить код",

                enabled = isFormValid,

                onClick = {

                    val database =
                        DatabaseProvider
                            .getDatabase(context)

                    CoroutineScope(
                        Dispatchers.IO
                    ).launch {

                        val user =
                            database
                                .userDao()
                                .getUserByPhone(phone)

                        withContext(
                            Dispatchers.Main
                        ) {

                            if (user != null) {

                                navController?.navigate(
                                    "verification/$phone"
                                )

                            } else {

                                errorMessage =
                                    "Пользователь не найден"
                            }
                        }
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(62.dp)
            )

            if (errorMessage.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                Text(
                    text = errorMessage,
                    color = Color.Red
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    LoginScreen()
}