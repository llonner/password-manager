package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
import java.security.MessageDigest

@Composable
fun BreachCheckScreen(
    navController: NavController? = null
) {

    var password by remember {
        mutableStateOf("")
    }

    var leakCount by remember {
        mutableIntStateOf(-1)
    }

    var loading by remember {
        mutableStateOf(false)
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    suspend fun checkPassword(password: String): Int {

        val sha1 = sha1(password)

        val prefix = sha1.take(5)

        val suffix = sha1.drop(5)

        val response = withContext(Dispatchers.IO) {

            val url =
                URL("https://api.pwnedpasswords.com/range/$prefix")

            val connection =
                url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"

            connection.inputStream
                .bufferedReader()
                .readText()
        }

        response.lines().forEach { line ->

            val parts = line.split(":")

            if (
                parts.size == 2 &&
                parts[0] == suffix
            ) {

                return parts[1]
                    .trim()
                    .toInt()
            }
        }

        return 0
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1E9))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            Text(
                text = "Проверка пароля",

                fontSize = 34.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF3F3F3F)
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text =
                    "Проверьте, встречался ли пароль в утечках данных.",

                fontSize = 16.sp,

                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(30.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),

                elevation = CardDefaults.cardElevation(
                    defaultElevation = 4.dp
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp)
                ) {

                    Row(
                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Surface(

                            shape =
                                RoundedCornerShape(16.dp),

                            color =
                                Color(0xFFE8F0FE)
                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.Security,

                                contentDescription = null,

                                tint =
                                    Color(0xFF2962FF),

                                modifier = Modifier
                                    .padding(12.dp)
                            )
                        }

                        Spacer(
                            modifier = Modifier.width(14.dp)
                        )

                        Column {

                            Text(
                                text = "Анализ пароля",

                                fontWeight =
                                    FontWeight.Bold,

                                fontSize = 20.sp
                            )

                            Text(
                                text =
                                    "Ваш пароль не отправляется полностью",

                                color = Color.Gray,

                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    OutlinedTextField(

                        value = password,

                        onValueChange = {
                            password = it
                        },

                        label = {
                            Text("Введите пароль")
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        visualTransformation =

                            if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),

                        trailingIcon = {

                            TextButton(
                                onClick = {

                                    passwordVisible =
                                        !passwordVisible
                                }
                            ) {

                                Text(

                                    if (passwordVisible)
                                        "Скрыть"
                                    else
                                        "Показать"
                                )
                            }
                        },

                        shape =
                            RoundedCornerShape(18.dp),

                        colors =
                            OutlinedTextFieldDefaults.colors(

                                focusedBorderColor =
                                    Color(0xFF2962FF),

                                unfocusedBorderColor =
                                    Color.LightGray
                            )
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    Button(

                        onClick = {

                            loading = true

                            leakCount = -1

                            kotlinx.coroutines
                                .CoroutineScope(
                                    Dispatchers.Main
                                )
                                .launch {

                                    leakCount =
                                        checkPassword(password)

                                    loading = false
                                }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),

                        enabled =
                            password.isNotBlank(),

                        shape =
                            RoundedCornerShape(18.dp),

                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    Color(0xFF2962FF)
                            )
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Security,

                            contentDescription = null
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = "Проверить пароль",

                            fontSize = 17.sp
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            if (loading) {

                Box(
                    modifier = Modifier.fillMaxWidth(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    CircularProgressIndicator(
                        color = Color(0xFF2962FF)
                    )
                }
            }

            if (leakCount >= 0) {

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape =
                        RoundedCornerShape(30.dp),

                    colors = CardDefaults.cardColors(

                        containerColor =

                            if (leakCount > 0)
                                Color(0xFFFFF0F0)
                            else
                                Color(0xFFEAF8EE)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp)
                    ) {

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically
                        ) {

                            Surface(

                                shape =
                                    RoundedCornerShape(16.dp),

                                color =

                                    if (leakCount > 0)
                                        Color(0xFFFFDADA)
                                    else
                                        Color(0xFFD9F5E2)
                            ) {

                                Icon(

                                    imageVector =

                                        if (leakCount > 0)
                                            Icons.Default.Warning
                                        else
                                            Icons.Default.VerifiedUser,

                                    contentDescription = null,

                                    tint =

                                        if (leakCount > 0)
                                            Color.Red
                                        else
                                            Color(0xFF2E7D32),

                                    modifier = Modifier
                                        .padding(12.dp)
                                )
                            }

                            Spacer(
                                modifier =
                                    Modifier.width(14.dp)
                            )

                            Column {

                                Text(

                                    text =

                                        if (leakCount > 0)
                                            "Пароль найден в утечках"
                                        else
                                            "Пароль безопасен",

                                    fontSize = 22.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =

                                        if (leakCount > 0)
                                            Color.Red
                                        else
                                            Color(0xFF2E7D32)
                                )

                                Spacer(
                                    modifier =
                                        Modifier.height(4.dp)
                                )

                                Text(

                                    text =

                                        if (leakCount > 0)
                                            "Найдены совпадения"
                                        else
                                            "Совпадений не найдено",

                                    color = Color.Gray
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(22.dp)
                        )

                        if (leakCount > 0) {

                            Text(

                                text =
                                    "Этот пароль встречается в утечках данных $leakCount раз.",

                                fontSize = 17.sp,

                                color =
                                    Color(0xFF3F3F3F)
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Card(

                                colors =
                                    CardDefaults.cardColors(
                                        containerColor =
                                            Color.White
                                    ),

                                shape =
                                    RoundedCornerShape(18.dp)
                            ) {

                                Text(

                                    text =
                                        "Рекомендуется срочно сменить пароль и не использовать его повторно.",

                                    modifier =
                                        Modifier.padding(18.dp),

                                    fontWeight =
                                        FontWeight.Medium,

                                    color =
                                        Color(0xFF3F3F3F)
                                )
                            }

                        } else {

                            Text(

                                text =
                                    "Пароль не найден в известных базах утечек.",

                                fontSize = 17.sp,

                                color =
                                    Color(0xFF3F3F3F)
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Card(

                                colors =
                                    CardDefaults.cardColors(
                                        containerColor =
                                            Color.White
                                    ),

                                shape =
                                    RoundedCornerShape(18.dp)
                            ) {

                                Text(

                                    text =
                                        "Отлично! Ваш пароль пока не был замечен в известных утечках.",

                                    modifier =
                                        Modifier.padding(18.dp),

                                    fontWeight =
                                        FontWeight.Medium,

                                    color =
                                        Color(0xFF2E7D32)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(24.dp)
            )
        }
    }
}

fun sha1(text: String): String {

    val md =
        MessageDigest.getInstance("SHA-1")

    val bytes =
        md.digest(text.toByteArray())

    return bytes.joinToString("") {

        "%02X".format(it)
    }
}