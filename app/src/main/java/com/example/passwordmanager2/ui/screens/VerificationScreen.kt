package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager2.R
import com.example.passwordmanager2.data.session.SessionManager
import com.example.passwordmanager2.ui.components.PrimaryButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun VerificationScreen(
    navController: NavController? = null,
    phone: String
) {

    val context = LocalContext.current

    var code1 by remember {
        mutableStateOf("")
    }

    var code2 by remember {
        mutableStateOf("")
    }

    var code3 by remember {
        mutableStateOf("")
    }

    var code4 by remember {
        mutableStateOf("")
    }

    var isError by remember {
        mutableStateOf(false)
    }

    val fullCode =
        code1 + code2 + code3 + code4

    val isCodeValid =
        fullCode.length == 4

    val focus2 = remember {
        FocusRequester()
    }

    val focus3 = remember {
        FocusRequester()
    }

    val focus4 = remember {
        FocusRequester()
    }

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
                    "Введите код\nподтверждения",

                fontSize = 32.sp,

                fontWeight = FontWeight.Bold,

                lineHeight = 38.sp,

                color = Color(0xFF3F3F3F)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text =
                    "Тестовый код — 7777",

                fontSize = 16.sp,

                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(6.dp)
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    horizontalAlignment =
                        Alignment.CenterHorizontally
                ) {

                    Row(

                        modifier = Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.Center
                    ) {

                        VerificationField(

                            value = code1,

                            onValueChange = {

                                if (it.length <= 1) {

                                    code1 =
                                        it.filter { char ->
                                            char.isDigit()
                                        }

                                    if (code1.isNotEmpty()) {

                                        focus2.requestFocus()
                                    }
                                }
                            },

                            isError = isError
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        VerificationField(

                            value = code2,

                            onValueChange = {

                                if (it.length <= 1) {

                                    code2 =
                                        it.filter { char ->
                                            char.isDigit()
                                        }

                                    if (code2.isNotEmpty()) {

                                        focus3.requestFocus()
                                    }
                                }
                            },

                            isError = isError,

                            modifier = Modifier
                                .focusRequester(focus2)
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        VerificationField(

                            value = code3,

                            onValueChange = {

                                if (it.length <= 1) {

                                    code3 =
                                        it.filter { char ->
                                            char.isDigit()
                                        }

                                    if (code3.isNotEmpty()) {

                                        focus4.requestFocus()
                                    }
                                }
                            },

                            isError = isError,

                            modifier = Modifier
                                .focusRequester(focus3)
                        )

                        Spacer(
                            modifier = Modifier.width(12.dp)
                        )

                        VerificationField(

                            value = code4,

                            onValueChange = {

                                if (it.length <= 1) {

                                    code4 =
                                        it.filter { char ->
                                            char.isDigit()
                                        }
                                }
                            },

                            isError = isError,

                            modifier = Modifier
                                .focusRequester(focus4)
                        )
                    }

                    if (isError) {

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = "Неверный код",
                            color = Color.Red
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            PrimaryButton(

                text = "Войти",

                enabled = isCodeValid,

                onClick = {

                    if (fullCode == "7777") {

                        isError = false

                        CoroutineScope(
                            Dispatchers.IO
                        ).launch {

                            SessionManager(context)
                                .saveUserPhone(phone)
                        }

                        navController?.navigate(
                            "home"
                        )

                    } else {

                        isError = true
                    }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(62.dp)
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}

@Composable
fun VerificationField(

    value: String,

    onValueChange: (String) -> Unit,

    isError: Boolean,

    modifier: Modifier = Modifier
) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        modifier = modifier
            .width(70.dp)
            .height(70.dp),

        singleLine = true,

        textStyle =
            TextStyle(
                fontSize = 24.sp
            ),

        keyboardOptions =
            KeyboardOptions(
                keyboardType =
                    KeyboardType.Number
            ),

        shape =
            MaterialTheme.shapes.medium,

        isError = isError
    )
}

@Preview(
    showBackground = true
)
@Composable
fun VerificationPreview() {

    VerificationScreen(
        phone = "9991234567"
    )
}