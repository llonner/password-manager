package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.data.local.entity.PasswordEntity
import com.example.passwordmanager2.data.session.SessionManager
import com.example.passwordmanager2.security.EncryptionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    var title by remember {
        mutableStateOf("")
    }

    var login by remember {
        mutableStateOf("")
    }

    var website by remember {
        mutableStateOf("")
    }

    val categories = listOf(
        "Соцсети",
        "Банки",
        "Игры",
        "Работа",
        "Почта",
        "Другое"
    )

    var selectedCategory by remember {
        mutableStateOf("Другое")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    // ПАРОЛЬ

    var password by remember {
        mutableStateOf(generatePassword(12))
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var passwordLength by remember {
        mutableIntStateOf(12)
    }

    var useNumbers by remember {
        mutableStateOf(true)
    }

    var useSymbols by remember {
        mutableStateOf(true)
    }

    var useLowercase by remember {
        mutableStateOf(true)
    }

    var useUppercase by remember {
        mutableStateOf(true)
    }

    val passwordStrength =
        calculatePasswordStrength(password)

    val strengthColor = when {

        passwordStrength < 0.35f ->
            Color(0xFFE53935)

        passwordStrength < 0.7f ->
            Color(0xFFFFB300)

        else ->
            Color(0xFF43A047)
    }

    val strengthText = when {

        passwordStrength < 0.35f ->
            "Слабый пароль"

        passwordStrength < 0.7f ->
            "Средний пароль"

        else ->
            "Надежный пароль"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1E9))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {

            Spacer(
                modifier = Modifier.height(55.dp)
            )

            Text(
                text = "Новая запись",

                fontSize = 34.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF3F3F3F)
            )

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // ОСНОВНАЯ КАРТОЧКА

            Card(

                modifier = Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(30.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )

            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {

                    // TITLE

                    OutlinedTextField(

                        value = title,

                        onValueChange = {
                            title = it
                        },

                        label = {
                            Text("Название")
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(18.dp),

                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    // LOGIN

                    OutlinedTextField(

                        value = login,

                        onValueChange = {
                            login = it
                        },

                        label = {
                            Text("Логин")
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(18.dp),

                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    // WEBSITE

                    OutlinedTextField(

                        value = website,

                        onValueChange = {
                            website = it
                        },

                        label = {
                            Text("Сайт")
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        shape =
                            RoundedCornerShape(18.dp),

                        singleLine = true
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    // CATEGORY

                    ExposedDropdownMenuBox(

                        expanded = expanded,

                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {

                        OutlinedTextField(

                            value = selectedCategory,

                            onValueChange = { },

                            readOnly = true,

                            label = {
                                Text("Категория")
                            },

                            trailingIcon = {

                                ExposedDropdownMenuDefaults
                                    .TrailingIcon(
                                        expanded = expanded
                                    )
                            },

                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),

                            shape =
                                RoundedCornerShape(18.dp)
                        )

                        ExposedDropdownMenu(

                            expanded = expanded,

                            onDismissRequest = {
                                expanded = false
                            }
                        ) {

                            categories.forEach { category ->

                                DropdownMenuItem(

                                    text = {
                                        Text(category)
                                    },

                                    onClick = {

                                        selectedCategory =
                                            category

                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(30.dp)
                    )

                    // PASSWORD TITLE

                    Text(
                        text = "Пароль",

                        fontSize = 24.sp,

                        fontWeight = FontWeight.Bold,

                        color = Color(0xFF3F3F3F)
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    // PASSWORD FIELD

                    OutlinedTextField(

                        value = password,

                        onValueChange = {
                            password = it
                        },

                        modifier =
                            Modifier.fillMaxWidth(),

                        visualTransformation =

                            if (passwordVisible)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),

                        trailingIcon = {

                            Row {

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

                                IconButton(

                                    onClick = {

                                        password =
                                            generatePassword(

                                                length =
                                                    passwordLength,

                                                useNumbers =
                                                    useNumbers,

                                                useSymbols =
                                                    useSymbols,

                                                useLowercase =
                                                    useLowercase,

                                                useUppercase =
                                                    useUppercase
                                            )
                                    }
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Refresh,

                                        contentDescription = null
                                    )
                                }
                            }
                        },

                        shape =
                            RoundedCornerShape(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.height(16.dp)
                    )

                    // СИЛА ПАРОЛЯ

                    LinearProgressIndicator(

                        progress = {
                            passwordStrength
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),

                        color = strengthColor,

                        trackColor =
                            Color(0xFFE6E6E6)
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(

                        text = strengthText,

                        color = strengthColor,

                        fontWeight = FontWeight.Bold,

                        fontSize = 15.sp
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )

                    // ДЛИНА

                    Text(

                        text =
                            "Длина пароля: $passwordLength",

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            Color(0xFF3F3F3F)
                    )

                    Slider(

                        value =
                            passwordLength.toFloat(),

                        onValueChange = {

                            passwordLength =
                                it.toInt()
                        },

                        valueRange = 6f..32f
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    // OPTIONS

                    PasswordOption(
                        text = "Цифры",
                        checked = useNumbers
                    ) {
                        useNumbers = it
                    }

                    PasswordOption(
                        text = "Символы",
                        checked = useSymbols
                    ) {
                        useSymbols = it
                    }

                    PasswordOption(
                        text = "Маленькие буквы",
                        checked = useLowercase
                    ) {
                        useLowercase = it
                    }

                    PasswordOption(
                        text = "Большие буквы",
                        checked = useUppercase
                    ) {
                        useUppercase = it
                    }

                    Spacer(
                        modifier = Modifier.height(28.dp)
                    )

                    // SAVE BUTTON

                    Button(

                        onClick = {

                            CoroutineScope(
                                Dispatchers.IO
                            ).launch {

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

                                    database
                                        .passwordDao()
                                        .insertPassword(

                                            PasswordEntity(

                                                title = title,

                                                login = login,

                                                password =
                                                    EncryptionManager.encrypt(
                                                        password
                                                    ),

                                                website = website,

                                                userPhone = phone,

                                                category =
                                                    selectedCategory
                                            )
                                        )

                                    withContext(
                                        Dispatchers.Main
                                    ) {

                                        navController
                                            ?.popBackStack()
                                    }
                                }
                            }
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .height(58.dp),

                        shape =
                            RoundedCornerShape(18.dp),

                        enabled =
                            title.isNotBlank() &&
                                    login.isNotBlank() &&
                                    password.isNotBlank()
                    ) {

                        Text(

                            text =
                                "Сохранить пароль",

                            fontSize = 17.sp
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
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

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),

        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Checkbox(
            checked = checked,
            onCheckedChange =
                onCheckedChange
        )

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        Text(
            text = text,

            fontSize = 16.sp,

            color = Color(0xFF3F3F3F)
        )
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
        chars += "!@#$%^&*()_-+=<>?/"

    if (useLowercase)
        chars +=
            "abcdefghijklmnopqrstuvwxyz"

    if (useUppercase)
        chars +=
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

    if (chars.isEmpty()) {

        chars =
            "abcdefghijklmnopqrstuvwxyz"
    }

    return (1..length)
        .map {
            chars.random()
        }
        .joinToString("")
}

fun calculatePasswordStrength(
    password: String
): Float {

    if (password.isEmpty()) {
        return 0f
    }

    var score = 0

    // Длина

    score += when {

        password.length >= 16 -> 40

        password.length >= 12 -> 30

        password.length >= 8 -> 20

        else -> 10
    }

    // lowercase

    if (password.any { it.isLowerCase() }) {
        score += 15
    }

    // uppercase

    if (password.any { it.isUpperCase() }) {
        score += 15
    }

    // digits

    if (password.any { it.isDigit() }) {
        score += 15
    }

    // symbols

    if (
        password.any {
            !it.isLetterOrDigit()
        }
    ) {
        score += 15
    }

    return (score.coerceAtMost(100)) / 100f
}