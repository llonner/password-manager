package com.example.passwordmanager2.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun KeyloggerProtectionScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    var password by remember {
        mutableStateOf("")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
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

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF2F1E9)
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                Text(
                    text = "Обход кейлогера",
                    fontSize = 30.sp
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text =
                        "Введите пароль и безопасно скопируйте его. " +
                                "Буфер обмена автоматически очистится через 15 секунд.",

                    color = Color.Gray
                )

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                OutlinedTextField(

                    value = password,

                    onValueChange = {
                        password = it
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Пароль")
                    },

                    shape = RoundedCornerShape(18.dp),

                    visualTransformation =

                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),

                    trailingIcon = {

                        IconButton(
                            onClick = {
                                passwordVisible =
                                    !passwordVisible
                            }
                        ) {

                            Icon(

                                imageVector =

                                    if (passwordVisible)
                                        Icons.Default.VisibilityOff
                                    else
                                        Icons.Default.Visibility,

                                contentDescription = null
                            )
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.height(24.dp)
                )

                Button(

                    onClick = {

                        copySecurely(
                            context = context,
                            text = password
                        )
                    },

                    modifier = Modifier.fillMaxWidth(),

                    enabled = password.isNotBlank()
                ) {

                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text("Безопасно скопировать")
                }
            }
        }
    }
}

fun copySecurely(
    context: Context,
    text: String
) {

    val clipboard =
        context.getSystemService(
            Context.CLIPBOARD_SERVICE
        ) as ClipboardManager

    val clip =
        ClipData.newPlainText(
            "password",
            text
        )

    clipboard.setPrimaryClip(clip)

    Toast.makeText(
        context,
        "Пароль скопирован",
        Toast.LENGTH_SHORT
    ).show()

    Handler(Looper.getMainLooper()).postDelayed({

        val emptyClip =
            ClipData.newPlainText("", "")

        clipboard.setPrimaryClip(emptyClip)

        Toast.makeText(
            context,
            "Буфер обмена очищен",
            Toast.LENGTH_SHORT
        ).show()

    }, 15000)
}