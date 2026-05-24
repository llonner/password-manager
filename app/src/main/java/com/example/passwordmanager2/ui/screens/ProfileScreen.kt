package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.ExitToApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.data.local.entity.UserEntity
import com.example.passwordmanager2.data.session.SessionManager
import kotlinx.coroutines.flow.first

@Composable
fun ProfileScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    var user by remember {
        mutableStateOf<UserEntity?>(null)
    }

    LaunchedEffect(Unit) {

        val sessionManager =
            SessionManager(context)

        val phone =
            sessionManager
                .currentUserPhone
                .first()

        if (phone != null) {

            val database =
                DatabaseProvider
                    .getDatabase(context)

            user = database
                .userDao()
                .getUserByPhone(phone)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1E9))
    ) {

        Scaffold(

            containerColor = Color(0xFFF2F1E9),

            bottomBar = {

                NavigationBar(
                    containerColor = Color(0xFFF2F1E9)
                ) {

                    NavigationBarItem(

                        selected = false,

                        onClick = {
                            navController?.navigate(
                                "home"
                            )
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Home,

                                contentDescription = null
                            )
                        },

                        label = {
                            Text("Главная")
                        }
                    )

                    NavigationBarItem(

                        selected = false,

                        onClick = {
                            navController?.navigate(
                                "useful"
                            )
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.List,

                                contentDescription = null
                            )
                        },

                        label = {
                            Text("Полезное")
                        }
                    )

                    NavigationBarItem(

                        selected = true,

                        onClick = { },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Person,

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
                    .padding(horizontal = 16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    ),

                horizontalAlignment =
                    Alignment.CenterHorizontally
            ) {

                Spacer(
                    modifier = Modifier.height(55.dp)
                )

                Surface(

                    modifier = Modifier
                        .size(125.dp)
                        .clip(CircleShape),

                    color = Color(0xFFC7EACB)
                ) {

                    Box(
                        contentAlignment =
                            Alignment.Center
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.Person,

                            contentDescription = null,

                            modifier =
                                Modifier.size(70.dp),

                            tint =
                                Color(0xFF4A4A4A)
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                Text(

                    text =
                        "${user?.firstName ?: ""} ${user?.lastName ?: ""}",

                    fontSize = 30.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color = Color(0xFF3F3F3F)
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(

                    text = if (
                        !user?.phone.isNullOrEmpty()
                    ) {
                        "+7 (${user?.phone?.take(3)}) " +
                                "${user?.phone?.drop(3)?.take(3)}-" +
                                "${user?.phone?.drop(6)?.take(2)}-" +
                                "${user?.phone?.drop(8)?.take(2)}"
                    } else {
                        ""
                    },

                    fontSize = 16.sp,

                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.height(36.dp)
                )

                ProfileCard(
                    title =
                        "Редактировать профиль"
                ) {

                    navController?.navigate(
                        "edit_profile"
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                ProfileCard(
                    title =
                        "Проверка безопасности"
                ) {

                    navController?.navigate(
                        "security_center"
                    )
                }
                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                Card(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                            CoroutineScope(
                                Dispatchers.IO
                            ).launch {

                                val sessionManager =
                                    SessionManager(context)

                                sessionManager.clearSession()

                                withContext(
                                    Dispatchers.Main
                                ) {

                                    navController?.navigate("login") {

                                        popUpTo(0)
                                    }
                                }
                            }
                        },

                    shape = RoundedCornerShape(24.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFECEC)
                    )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 18.dp,
                                vertical = 22.dp
                            ),

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Icon(

                            imageVector =
                                Icons.Default.ExitToApp,

                            contentDescription = null,

                            tint = Color.Red
                        )

                        Spacer(
                            modifier = Modifier.width(14.dp)
                        )

                        Text(

                            text = "Выйти из аккаунта",

                            fontSize = 18.sp,

                            fontWeight = FontWeight.Medium,

                            color = Color.Red
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileCard(

    title: String,

    onClick: () -> Unit
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp),

        shape =
            RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        onClick = onClick
    ) {

        Box(

            modifier = Modifier.fillMaxSize(),

            contentAlignment =
                Alignment.CenterStart
        ) {

            Text(

                text = title,

                modifier = Modifier.padding(
                    horizontal = 20.dp
                ),

                fontSize = 18.sp,

                fontWeight =
                    FontWeight.Medium,

                color = Color(0xFF3F3F3F)
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ProfileScreenPreview() {

    ProfileScreen()
}