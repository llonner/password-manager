package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun UsefulScreen(
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
                .padding(8.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF2F1E9)
            )
        ) {

            Scaffold(

                containerColor = Color.Transparent,

                topBar = {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),

                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "Полезное",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A4A4A)
                        )
                    }
                },

                bottomBar = {

                    NavigationBar(
                        containerColor = Color(0xFFF2F1E9)
                    ) {

                        NavigationBarItem(
                            selected = false,

                            onClick = {
                                navController?.navigate("home")
                            },

                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null
                                )
                            },

                            label = {
                                Text("Главная")
                            }
                        )

                        NavigationBarItem(
                            selected = true,
                            onClick = { },

                            icon = {
                                Icon(
                                    imageVector = Icons.Default.List,
                                    contentDescription = null
                                )
                            },

                            label = {
                                Text("Полезное")
                            }
                        )

                        NavigationBarItem(
                            selected = false,

                            onClick = {
                                navController?.navigate("profile")
                            },

                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null
                                )
                            },

                            label = {
                                Text("Профиль")
                            }
                        )
                    }
                }

            ) { paddingValues ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),

                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    UsefulCard(
                        title = "Проверка пароля на слив",
                        description =
                            "Проверка, был ли пароль опубликован в утечках данных"
                    ) {

                        navController?.navigate("breach_check")
                    }

                    UsefulCard(
                        title = "Обход кейлогера",
                        description =
                            "Безопасный ввод пароля против кейлогеров"
                    ) {

                        navController?.navigate("anti_keylogger")
                    }
                }
            }
        }
    }
}

@Composable
fun UsefulCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },

        shape = RoundedCornerShape(24.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = description,
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}