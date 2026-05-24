package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.data.local.entity.PasswordEntity
import com.example.passwordmanager2.data.session.SessionManager
import com.example.passwordmanager2.security.EncryptionManager
import kotlinx.coroutines.flow.first

@Composable
fun SecurityCenterScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    var passwords by remember {
        mutableStateOf<List<PasswordEntity>>(emptyList())
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
                DatabaseProvider.getDatabase(context)

            passwords =
                database.passwordDao()
                    .getPasswordsListByPhone(phone)
        }
    }

    val weakPasswords =
        passwords.count {

            try {

                EncryptionManager
                    .decrypt(it.password)
                    .length < 8

            } catch (e: Exception) {

                false
            }
        }

    val duplicatePasswords =
        passwords
            .map {

                try {

                    EncryptionManager
                        .decrypt(it.password)

                } catch (e: Exception) {

                    ""
                }
            }
            .groupingBy { it }
            .eachCount()
            .count { it.value > 1 }

    val strongPasswords =
        passwords.size - weakPasswords

    val securityScore = when {

        passwords.isEmpty() -> 0

        weakPasswords == 0 &&
                duplicatePasswords == 0 -> 100

        else -> {

            var score = 100

            score -= weakPasswords * 15
            score -= duplicatePasswords * 20

            score.coerceAtLeast(0)
        }
    }

    val scoreColor = when {

        securityScore < 40 ->
            Color.Red

        securityScore < 70 ->
            Color(0xFFFF9800)

        else ->
            Color(0xFF4CAF50)
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

            shape = RoundedCornerShape(28.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF2F1E9)
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(22.dp)
            ) {

                Text(
                    text = "Центр безопасности",

                    fontSize = 30.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF222222)
                )

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                if (passwords.isEmpty()) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 90.dp),

                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Security,

                                contentDescription = null,

                                modifier =
                                    Modifier.size(90.dp),

                                tint = Color.Gray
                            )

                            Spacer(
                                modifier = Modifier.height(18.dp)
                            )

                            Text(
                                text = "Нет сохранённых паролей",

                                fontSize = 22.sp,

                                fontWeight = FontWeight.Bold,

                                color = Color.DarkGray
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Text(
                                text =
                                    "Добавьте первый пароль,\nчтобы проверить безопасность",

                                fontSize = 16.sp,

                                color = Color.Gray,

                                lineHeight = 24.sp
                            )
                        }
                    }

                } else {

                    Card(

                        shape = RoundedCornerShape(24.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )

                    ) {

                        Column(
                            modifier = Modifier.padding(22.dp)
                        ) {

                            Text(
                                text = "$securityScore/100",

                                fontSize = 42.sp,

                                fontWeight = FontWeight.Bold,

                                color = scoreColor
                            )

                            Spacer(
                                modifier = Modifier.height(14.dp)
                            )

                            LinearProgressIndicator(

                                progress = {
                                    securityScore / 100f
                                },

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp),

                                color = scoreColor,

                                trackColor =
                                    Color.LightGray
                            )

                            Spacer(
                                modifier = Modifier.height(24.dp)
                            )

                            SecurityItem(
                                title = "Всего паролей",
                                value = passwords.size.toString()
                            )

                            SecurityItem(
                                title = "Надёжные",
                                value = strongPasswords.toString()
                            )

                            SecurityItem(
                                title = "Слабые",
                                value = weakPasswords.toString()
                            )

                            SecurityItem(
                                title = "Повторяются",
                                value = duplicatePasswords.toString()
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(28.dp)
                    )

                    Text(
                        text = "Рекомендации",

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    if (
                        weakPasswords == 0 &&
                        duplicatePasswords == 0
                    ) {

                        RecommendationCard(
                            text =
                                "Отлично! Ваши пароли хорошо защищены."
                        )

                    } else {

                        if (weakPasswords > 0) {

                            RecommendationCard(
                                text =
                                    "Обнаружены слабые пароли. Лучше использовать длину от 12 символов."
                            )
                        }

                        if (duplicatePasswords > 0) {

                            RecommendationCard(
                                text =
                                    "Некоторые пароли повторяются. Используйте уникальные комбинации."
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SecurityItem(
    title: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),

        horizontalArrangement =
            Arrangement.SpaceBetween
    ) {

        Text(
            text = title,
            fontSize = 17.sp,
            color = Color.DarkGray
        )

        Text(
            text = value,

            fontSize = 18.sp,

            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RecommendationCard(
    text: String
) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),

        shape = RoundedCornerShape(20.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(
            modifier = Modifier.padding(18.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Warning,

                contentDescription = null,

                tint = Color(0xFFFF9800)
            )

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = text,

                fontSize = 16.sp,

                lineHeight = 22.sp
            )
        }
    }
}