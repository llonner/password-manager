package com.example.passwordmanager2.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.data.local.entity.PasswordEntity
import com.example.passwordmanager2.security.EncryptionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PasswordDetailsScreen(
    navController: NavController? = null,
    passwordId: Int
) {

    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val scope = rememberCoroutineScope()

    var item by remember {
        mutableStateOf<PasswordEntity?>(null)
    }

    var showPassword by remember {
        mutableStateOf(false)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var isFavorite by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {

        val database =
            DatabaseProvider.getDatabase(context)

        item =
            database.passwordDao()
                .getPasswordById(passwordId)

        isFavorite = item?.isFavorite ?: false
    }

    if (item == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator()
        }

        return
    }

    val password = try {

        EncryptionManager.decrypt(item!!.password)

    } catch (e: Exception) {

        ""
    }

    val faviconUrl =
        if (item!!.website.isNotBlank()) {

            "https://www.google.com/s2/favicons?sz=128&domain=${item!!.website}"

        } else ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F2EC))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navController?.popBackStack()
                    }
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.ArrowBack,
                        contentDescription = null
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = "Назад",
                        fontSize = 18.sp
                    )
                }

                Row {

                    IconButton(
                        onClick = {

                            isFavorite = !isFavorite

                            scope.launch(Dispatchers.IO) {

                                val db =
                                    DatabaseProvider
                                        .getDatabase(context)

                                db.passwordDao()
                                    .updatePassword(
                                        item!!.copy(
                                            isFavorite =
                                                isFavorite
                                        )
                                    )
                            }
                        }
                    ) {

                        Icon(
                            imageVector =
                                if (isFavorite)
                                    Icons.Default.Star
                                else
                                    Icons.Outlined.StarOutline,

                            contentDescription = null,

                            tint =
                                if (isFavorite)
                                    Color(0xFFFFC107)
                                else
                                    Color.Gray
                        )
                    }

                    IconButton(
                        onClick = {
                            showDeleteDialog = true
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Delete,

                            contentDescription = null,

                            tint = Color.Red
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier.size(72.dp),
                    shape = CircleShape,
                    color = Color.White,
                    tonalElevation = 4.dp
                ) {

                    if (faviconUrl.isNotEmpty()) {

                        Image(
                            painter =
                                rememberAsyncImagePainter(
                                    faviconUrl
                                ),

                            contentDescription = null,

                            modifier =
                                Modifier.padding(16.dp)
                        )

                    } else {

                        Box(
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(
                                text =
                                    item!!.title
                                        .first()
                                        .uppercase(),

                                fontSize = 30.sp,
                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.width(18.dp)
                )

                Column {

                    Text(
                        text = item!!.title,
                        fontSize = 28.sp,
                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text =
                            item!!.website.ifBlank {
                                "Без сайта"
                            },

                        color = Color.Gray
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            DetailCard(
                title = "Логин",
                value = item!!.login
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Card(
                shape =
                    RoundedCornerShape(24.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier.padding(18.dp)
                ) {

                    Text(
                        text = "Пароль",
                        color = Color.Gray
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    OutlinedTextField(

                        value = password,

                        onValueChange = { },

                        readOnly = true,

                        modifier =
                            Modifier.fillMaxWidth(),

                        visualTransformation =

                            if (showPassword)
                                VisualTransformation.None
                            else
                                PasswordVisualTransformation(),

                        trailingIcon = {

                            IconButton(
                                onClick = {
                                    showPassword =
                                        !showPassword
                                }
                            ) {

                                Icon(

                                    imageVector =

                                        if (showPassword)
                                            Icons.Default.VisibilityOff
                                        else
                                            Icons.Default.Visibility,

                                    contentDescription = null
                                )
                            }
                        }
                    )

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    Row(
                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {

                        OutlinedButton(
                            onClick = {

                                clipboard.setText(
                                    AnnotatedString(password)
                                )

                                Toast.makeText(
                                    context,
                                    "Пароль скопирован",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },

                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.ContentCopy,

                                contentDescription = null
                            )

                            Spacer(
                                modifier =
                                    Modifier.width(8.dp)
                            )

                            Text("Копировать")
                        }

                        Button(
                            onClick = {

                                navController?.navigate(
                                    "edit_password/${item!!.id}"
                                )
                            },

                            modifier =
                                Modifier.weight(1f)
                        ) {

                            Icon(
                                imageVector =
                                    Icons.Default.Edit,

                                contentDescription = null
                            )

                            Spacer(
                                modifier =
                                    Modifier.width(8.dp)
                            )

                            Text("Изменить")
                        }
                    }
                }
            }
        }

        if (showDeleteDialog) {

            AlertDialog(

                onDismissRequest = {
                    showDeleteDialog = false
                },

                confirmButton = {

                    Button(

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),

                        onClick = {

                            scope.launch(Dispatchers.IO) {

                                val db =
                                    DatabaseProvider
                                        .getDatabase(context)

                                db.passwordDao()
                                    .deletePassword(item!!)

                                withContext(
                                    Dispatchers.Main
                                ) {

                                    navController
                                        ?.popBackStack()
                                }
                            }
                        }
                    ) {

                        Text("Удалить")
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
                    Text("Удалить запись?")
                },

                text = {
                    Text(
                        "Это действие нельзя отменить."
                    )
                }
            )
        }
    }
}

@Composable
fun DetailCard(

    title: String,

    value: String
) {

    Card(

        modifier = Modifier
            .fillMaxWidth(),

        shape =
            RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Text(

                text = title,

                fontSize = 15.sp,

                color = Color.Gray
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(

                text = value,

                fontSize = 20.sp,

                fontWeight = FontWeight.Medium,

                color = Color(0xFF3F3F3F),

                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}