package com.example.passwordmanager2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.security.EncryptionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun EditPasswordScreen(
    navController: NavController,
    passwordId: Int
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }

    var loading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {

        val database =
            DatabaseProvider.getDatabase(context)

        val item =
            database.passwordDao()
                .getPasswordById(passwordId)

        if (item != null) {

            title = item.title
            login = item.login
            password =
                EncryptionManager.decrypt(item.password)

            website = item.website
        }

        loading = false
    }

    if (loading) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {

            CircularProgressIndicator()
        }

        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1E9))
            .padding(20.dp)
    ) {

        Row {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Изменение",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text("Название")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = login,
            onValueChange = {
                login = it
            },
            label = {
                Text("Логин")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Пароль")
            },
            visualTransformation =
                PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = website,
            onValueChange = {
                website = it
            },
            label = {
                Text("Сайт")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {

                scope.launch(Dispatchers.IO) {

                    val database =
                        DatabaseProvider.getDatabase(context)

                    val oldItem =
                        database.passwordDao()
                            .getPasswordById(passwordId)

                    if (oldItem != null) {

                        database.passwordDao().updatePassword(

                            oldItem.copy(
                                title = title,
                                login = login,
                                password =
                                    EncryptionManager.encrypt(password),
                                website = website
                            )
                        )
                    }

                    launch(Dispatchers.Main) {

                        Toast.makeText(
                            context,
                            "Пароль обновлен",
                            Toast.LENGTH_SHORT
                        ).show()

                        navController.popBackStack()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {

            Text("Сохранить")
        }
    }
}