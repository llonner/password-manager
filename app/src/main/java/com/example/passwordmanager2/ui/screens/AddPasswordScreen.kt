package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanager2.ui.components.PrimaryButton
import kotlin.random.Random

@Composable
fun AddPasswordScreen() {

    var title by remember {
        mutableStateOf("")
    }

    var login by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf(generatePassword(12))
    }

    var passwordLength by remember {
        mutableIntStateOf(12)
    }

    var useNumbers by remember {
        mutableStateOf(true)
    }

    var useSymbols by remember {
        mutableStateOf(false)
    }

    var useLowercase by remember {
        mutableStateOf(true)
    }

    var useUppercase by remember {
        mutableStateOf(true)
    }

    val passwordStrength = calculatePasswordStrength(password)

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
                    .verticalScroll(rememberScrollState())
            ) {

                Text(
                    text = "Новая запись",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A4A4A)
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    label = {
                        Text("Название")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = login,
                    onValueChange = {
                        login = it
                    },
                    label = {
                        Text("Логин или Email")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Пароль",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = {

                        IconButton(
                            onClick = {

                                password = generatePassword(
                                    length = passwordLength,
                                    useNumbers = useNumbers,
                                    useSymbols = useSymbols,
                                    useLowercase = useLowercase,
                                    useUppercase = useUppercase
                                )
                            }
                        ) {

                            Text("⟳")
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                LinearProgressIndicator(
                    progress = {
                        passwordStrength
                    },
                    modifier = Modifier.fillMaxWidth(),
                    color = when {
                        passwordStrength < 0.3f -> Color.Red
                        passwordStrength < 0.7f -> Color.Yellow
                        else -> Color(0xFF4CAF50)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Длина: $passwordLength"
                )

                Slider(
                    value = passwordLength.toFloat(),

                    onValueChange = {

                        passwordLength = it.toInt()

                        password = generatePassword(
                            length = passwordLength,
                            useNumbers = useNumbers,
                            useSymbols = useSymbols,
                            useLowercase = useLowercase,
                            useUppercase = useUppercase
                        )
                    },

                    valueRange = 6f..32f
                )

                Spacer(modifier = Modifier.height(16.dp))

                PasswordOption(
                    text = "Цифры",
                    checked = useNumbers,
                    onCheckedChange = {
                        useNumbers = it
                    }
                )

                PasswordOption(
                    text = "Символы",
                    checked = useSymbols,
                    onCheckedChange = {
                        useSymbols = it
                    }
                )

                PasswordOption(
                    text = "Маленькие буквы",
                    checked = useLowercase,
                    onCheckedChange = {
                        useLowercase = it
                    }
                )

                PasswordOption(
                    text = "Большие буквы",
                    checked = useUppercase,
                    onCheckedChange = {
                        useUppercase = it
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                PrimaryButton(
                    text = "Сохранить пароль",
                    enabled = title.isNotBlank() &&
                            login.isNotBlank(),

                    onClick = {

                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {

                    },

                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text("Добавить вручную")
                }
            }
        }
    }
}

@Composable
fun PasswordOption(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange
        )

        Text(text)
    }
}

fun generatePassword(
    length: Int,
    useNumbers: Boolean = true,
    useSymbols: Boolean = false,
    useLowercase: Boolean = true,
    useUppercase: Boolean = true
): String {

    var chars = ""

    if (useNumbers)
        chars += "0123456789"

    if (useSymbols)
        chars += "!@#$%^&*()"

    if (useLowercase)
        chars += "abcdefghijklmnopqrstuvwxyz"

    if (useUppercase)
        chars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    if (chars.isEmpty()) {
        chars = "abcdefghijklmnopqrstuvwxyz"
    }

    return (1..length)
        .map {
            chars.random()
        }
        .joinToString("")
}

fun calculatePasswordStrength(password: String): Float {

    return when {
        password.length < 8 -> 0.2f
        password.length < 12 -> 0.5f
        else -> 1f
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddPasswordScreenPreview() {

    AddPasswordScreen()
}