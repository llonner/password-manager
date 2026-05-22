package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
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

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 20.dp
                            ),

                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        IconButton(
                            onClick = {

                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = null
                            )
                        }

                        Text(
                            text = "Пароли",
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4A4A4A)
                        )

                        IconButton(
                            onClick = {
                                navController?.navigate("add_password")
                            }
                        ) {

                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }
                },

                bottomBar = {

                    NavigationBar(
                        containerColor = Color(0xFFF2F1E9)
                    ) {

                        NavigationBarItem(
                            selected = true,
                            onClick = { },

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
                            selected = false,
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
                            onClick = { },

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

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "Здесь пока ничего нет",
                        color = Color.LightGray,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {

    HomeScreen()
}