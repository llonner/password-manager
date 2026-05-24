package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.data.session.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    val sessionManager =
        SessionManager(context)

    var savedPin by remember {
        mutableStateOf<String?>(null)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var showPinDeleteDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        savedPin =
            sessionManager
                .currentPin
                .first()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1E9))
    ) {

        Column(

            modifier = Modifier
                .fillMaxSize()
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

            Row(

                modifier = Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                IconButton(

                    onClick = {

                        navController?.popBackStack()
                    }
                ) {

                    Text(

                        text = "←",

                        fontSize = 26.sp,

                        color = Color(0xFF3F3F3F)
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(

                    text = "      Настройки",

                    fontSize = 32.sp,

                    fontWeight = FontWeight.Bold,

                    color = Color(0xFF3F3F3F)
                )
            }

            Spacer(
                modifier = Modifier.height(35.dp)
            )

            if (savedPin.isNullOrEmpty()) {

                SettingsCard(

                    title = "Установить PIN-код",

                    onClick = {

                        navController?.navigate(
                            "pin_setup"
                        )
                    }
                )

            } else {

                SettingsCard(

                    title = "Изменить PIN-код",

                    onClick = {

                        navController?.navigate(
                            "pin_setup"
                        )
                    }
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                SettingsCard(

                    title = "Отключить PIN-код",

                    onClick = {

                        showPinDeleteDialog = true
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            SettingsCard(

                title = "Очистить все пароли",

                onClick = {

                    showDeleteDialog = true
                }
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Text(

                text = "Версия приложения 1.0",

                fontSize = 15.sp,

                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }

        if (showDeleteDialog) {

            AlertDialog(

                onDismissRequest = {

                    showDeleteDialog = false
                },

                confirmButton = {

                    TextButton(

                        onClick = {

                            CoroutineScope(
                                Dispatchers.IO
                            ).launch {

                                val database =
                                    DatabaseProvider
                                        .getDatabase(context)

                                database
                                    .passwordDao()
                                    .deleteAllPasswords()

                                showDeleteDialog = false
                            }
                        }
                    ) {

                        Text(

                            text = "Удалить",

                            color = Color.Red
                        )
                    }
                },

                dismissButton = {

                    TextButton(

                        onClick = {

                            showDeleteDialog = false
                        }
                    ) {

                        Text("Отмена")
                    }
                },

                title = {

                    Text(
                        text = "Удалить все пароли?"
                    )
                },

                text = {

                    Text(
                        text =
                            "Это действие нельзя отменить"
                    )
                },

                containerColor = Color.White
            )
        }

        if (showPinDeleteDialog) {

            AlertDialog(

                onDismissRequest = {

                    showPinDeleteDialog = false
                },

                confirmButton = {

                    TextButton(

                        onClick = {

                            CoroutineScope(
                                Dispatchers.IO
                            ).launch {

                                sessionManager.clearPin()

                                savedPin = null

                                showPinDeleteDialog =
                                    false
                            }
                        }
                    ) {

                        Text(

                            text = "Отключить",

                            color = Color.Red
                        )
                    }
                },

                dismissButton = {

                    TextButton(

                        onClick = {

                            showPinDeleteDialog = false
                        }
                    ) {

                        Text("Отмена")
                    }
                },

                title = {

                    Text(
                        text = "Отключить PIN-код?"
                    )
                },

                text = {

                    Text(
                        text =
                            "Защита приложения будет отключена"
                    )
                },

                containerColor = Color.White
            )
        }
    }
}

@Composable
fun SettingsCard(

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
fun SettingsScreenPreview() {

    SettingsScreen()
}