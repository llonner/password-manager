package com.example.passwordmanager2.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.data.local.entity.UserEntity
import com.example.passwordmanager2.data.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun EditProfileScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    val sessionManager = remember {
        SessionManager(context)
    }

    val scope = rememberCoroutineScope()

    var user by remember {
        mutableStateOf<UserEntity?>(null)
    }

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var phone by remember {
        mutableStateOf("")
    }

    var avatarUri by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        val currentPhone =
            sessionManager
                .currentUserPhone
                .first() ?: return@LaunchedEffect

        val database =
            DatabaseProvider.getDatabase(context)

        val currentUser =
            database.userDao()
                .getUserByPhone(currentPhone)

        user = currentUser

        currentUser?.let {

            firstName = it.firstName
            lastName = it.lastName
            phone = it.phone
            avatarUri = it.avatarUri
        }
    }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            if (uri != null) {
                avatarUri = uri.toString()
            }
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

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            navController?.popBackStack()
                        }
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "Профиль",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF222222)
                    )
                }

                Spacer(
                    modifier = Modifier.height(34.dp)
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .size(120.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFE0E0E0))
                                    .clickable {
                                        imagePickerLauncher.launch("image/*")
                                    },

                                contentAlignment = Alignment.Center
                            ) {

                                if (avatarUri.isNotEmpty()) {

                                    Image(
                                        painter =
                                            rememberAsyncImagePainter(
                                                avatarUri
                                            ),

                                        contentDescription = null,

                                        modifier =
                                            Modifier.fillMaxSize(),

                                        contentScale =
                                            ContentScale.Crop
                                    )

                                } else {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Person,

                                        contentDescription = null,

                                        modifier =
                                            Modifier.size(54.dp),

                                        tint =
                                            Color.DarkGray
                                    )
                                }
                            }

                            Surface(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .size(36.dp)
                                    .clickable {
                                        imagePickerLauncher.launch("image/*")
                                    },

                                shape = CircleShape,

                                color = Color(0xFF56659A)
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    Icon(
                                        imageVector =
                                            Icons.Default.Edit,

                                        contentDescription = null,

                                        tint = Color.White,

                                        modifier =
                                            Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(14.dp)
                        )

                        Text(
                            text = "Изменить фото",

                            fontSize = 15.sp,

                            color = Color(0xFF56659A),

                            fontWeight = FontWeight.Medium,

                            modifier = Modifier.clickable {
                                imagePickerLauncher.launch("image/*")
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(36.dp)
                )

                ProfileInputField(
                    label = "Имя",
                    value = firstName,
                    onValueChange = {
                        firstName = it
                    }
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                ProfileInputField(
                    label = "Фамилия",
                    value = lastName,
                    onValueChange = {
                        lastName = it
                    }
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                OutlinedTextField(

                    value = "+7 $phone",

                    onValueChange = { },

                    readOnly = true,

                    label = {
                        Text("Телефон")
                    },

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    )
                )

                Spacer(
                    modifier = Modifier.height(34.dp)
                )

                Button(

                    onClick = {

                        scope.launch(Dispatchers.IO) {

                            user?.let { currentUser ->

                                val updatedUser =
                                    currentUser.copy(

                                        firstName = firstName,

                                        lastName = lastName,

                                        avatarUri = avatarUri
                                    )

                                val database =
                                    DatabaseProvider.getDatabase(context)

                                database
                                    .userDao()
                                    .updateUser(updatedUser)

                                withContext(Dispatchers.Main) {

                                    navController?.popBackStack()
                                }
                            }
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(58.dp),

                    shape = RoundedCornerShape(18.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF56659A)
                    )
                ) {

                    Text(
                        text = "Сохранить изменения",
                        fontSize = 17.sp
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )
            }
        }
    }
}

@Composable
fun ProfileInputField(

    label: String,

    value: String,

    onValueChange: (String) -> Unit
) {

    OutlinedTextField(

        value = value,

        onValueChange = onValueChange,

        label = {
            Text(label)
        },

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(18.dp),

        singleLine = true,

        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}