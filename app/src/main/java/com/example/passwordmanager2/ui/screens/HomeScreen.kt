package com.example.passwordmanager2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.passwordmanager2.data.local.DatabaseProvider
import com.example.passwordmanager2.data.local.entity.PasswordEntity
import com.example.passwordmanager2.data.session.SessionManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first

@Composable
fun HomeScreen(
    navController: NavController? = null
) {

    val context = LocalContext.current

    var passwords by remember {
        mutableStateOf<List<PasswordEntity>>(emptyList())
    }

    var searchQuery by remember {
        mutableStateOf("")
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

            database
                .passwordDao()
                .getPasswordsByPhone(phone)
                .collectLatest {

                    passwords = it
                }
        }
    }

    val filteredPasswords = passwords.filter {

        it.title.contains(
            searchQuery,
            ignoreCase = true
        ) ||

                it.login.contains(
                    searchQuery,
                    ignoreCase = true
                ) ||

                it.website.contains(
                    searchQuery,
                    ignoreCase = true
                )
    }

    val favoritePasswords =
        filteredPasswords.filter {
            it.isFavorite
        }

    val socialPasswords =
        filteredPasswords.filter {
            it.category == "Соцсети" &&
                    !it.isFavorite
        }

    val bankPasswords =
        filteredPasswords.filter {
            it.category == "Банки" &&
                    !it.isFavorite
        }

    val gamePasswords =
        filteredPasswords.filter {
            it.category == "Игры" &&
                    !it.isFavorite
        }

    val workPasswords =
        filteredPasswords.filter {
            it.category == "Работа" &&
                    !it.isFavorite
        }

    val mailPasswords =
        filteredPasswords.filter {
            it.category == "Почта" &&
                    !it.isFavorite
        }

    val otherPasswords =
        filteredPasswords.filter {
            it.category == "Другое" &&
                    !it.isFavorite
        }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F1E9))
    ) {

        Scaffold(

            containerColor = Color(0xFFF2F1E9),

            topBar = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp,
                            vertical = 20.dp
                        ),

                    verticalAlignment =
                        Alignment.CenterVertically,

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    IconButton(

                        onClick = {

                            navController?.navigate(
                                "settings"
                            )
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Settings,

                            contentDescription = null,

                            tint = Color(0xFF3F3F3F)
                        )
                    }

                    Text(

                        text = "Пароли",

                        fontSize = 32.sp,

                        fontWeight =
                            FontWeight.Bold,

                        color = Color(0xFF3F3F3F)
                    )

                    IconButton(

                        onClick = {

                            navController?.navigate(
                                "add_password"
                            )
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Add,

                            contentDescription = null,

                            tint = Color(0xFF3F3F3F)
                        )
                    }
                }
            },

            bottomBar = {

                NavigationBar(
                    containerColor = Color(0xFFF2F1E9)
                ) {

                    NavigationBarItem(

                        selected = true,

                        onClick = { },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Home,

                                contentDescription = null
                            )
                        },

                        label = {
                            Text("Главная")
                        }
                    )

                    NavigationBarItem(

                        selected = false,

                        onClick = {

                            navController?.navigate(
                                "useful"
                            )
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.List,

                                contentDescription = null
                            )
                        },

                        label = {
                            Text("Полезное")
                        }
                    )

                    NavigationBarItem(

                        selected = false,

                        onClick = {

                            navController?.navigate(
                                "profile"
                            )
                        },

                        icon = {

                            Icon(
                                imageVector =
                                    Icons.Default.Person,

                                contentDescription = null
                            )
                        },

                        label = {
                            Text("Профиль")
                        }
                    )
                }
            }

        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                if (passwords.isNotEmpty()) {

                    OutlinedTextField(

                        value = searchQuery,

                        onValueChange = {
                            searchQuery = it
                        },

                        label = {
                            Text("Поиск")
                        },

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),

                        shape =
                            RoundedCornerShape(18.dp),

                        singleLine = true
                    )
                }

                if (passwords.isEmpty()) {

                    Box(
                        modifier = Modifier.fillMaxSize(),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(

                            text =
                                "Здесь пока ничего нет\nнажмите + чтобы добавить пароль",

                            color = Color.Gray,

                            fontSize = 20.sp,

                            lineHeight = 30.sp
                        )
                    }

                } else {

                    LazyColumn(

                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)

                    ) {

                        passwordSection(
                            title = "Избранное",
                            passwordList = favoritePasswords,
                            navController = navController
                        )

                        passwordSection(
                            title = "Соцсети",
                            passwordList = socialPasswords,
                            navController = navController
                        )

                        passwordSection(
                            title = "Банки",
                            passwordList = bankPasswords,
                            navController = navController
                        )

                        passwordSection(
                            title = "Игры",
                            passwordList = gamePasswords,
                            navController = navController
                        )

                        passwordSection(
                            title = "Работа",
                            passwordList = workPasswords,
                            navController = navController
                        )

                        passwordSection(
                            title = "Почта",
                            passwordList = mailPasswords,
                            navController = navController
                        )

                        passwordSection(
                            title = "Другое",
                            passwordList = otherPasswords,
                            navController = navController
                        )

                        item {
                            Spacer(
                                modifier = Modifier.height(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun LazyListScope.passwordSection(

    title: String,

    passwordList: List<PasswordEntity>,

    navController: NavController?
) {

    if (passwordList.isNotEmpty()) {

        item {

            Text(

                text = title,

                fontSize = 22.sp,

                fontWeight = FontWeight.Bold,

                color = Color(0xFF3F3F3F),

                modifier = Modifier.padding(
                    top = 18.dp,
                    bottom = 10.dp
                )
            )
        }

        items(passwordList) { item ->

            PasswordCard(
                item = item,
                navController = navController
            )
        }
    }
}

@Composable
fun PasswordCard(

    item: PasswordEntity,

    navController: NavController?
) {

    val faviconUrl =
        if (item.website.isNotBlank()) {
            "https://www.google.com/s2/favicons?sz=128&domain=${item.website}"
        } else {
            ""
        }

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),

        onClick = {

            navController?.navigate(
                "password_details/${item.id}"
            )
        },

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment =
                Alignment.CenterVertically,

            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                // ICON

                Surface(

                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape),

                    color = Color(0xFFF5F5F5)
                ) {

                    if (faviconUrl.isNotEmpty()) {

                        Image(

                            painter =
                                rememberAsyncImagePainter(
                                    faviconUrl
                                ),

                            contentDescription = null,

                            modifier = Modifier
                                .padding(12.dp),

                            contentScale =
                                ContentScale.Fit
                        )

                    } else {

                        Box(
                            contentAlignment =
                                Alignment.Center
                        ) {

                            Text(

                                text = item.title
                                    .first()
                                    .uppercase(),

                                fontWeight =
                                    FontWeight.Bold,

                                fontSize = 22.sp,

                                color =
                                    Color(0xFF3F3F3F)
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.width(14.dp)
                )

                Column {

                    Text(

                        text = item.title,

                        fontWeight =
                            FontWeight.Bold,

                        fontSize = 19.sp,

                        color =
                            Color(0xFF3F3F3F)
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(

                        text = item.login,

                        color = Color.Gray,

                        fontSize = 14.sp
                    )
                }
            }

            if (item.isFavorite) {

                Icon(

                    imageVector =
                        Icons.Default.Star,

                    contentDescription = null,

                    tint = Color(0xFFFFC107)
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {

    HomeScreen()
}