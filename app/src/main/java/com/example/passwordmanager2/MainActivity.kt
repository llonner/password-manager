package com.example.passwordmanager2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.passwordmanager2.navigation.AppNavigation
import com.example.passwordmanager2.ui.theme.PasswordManager2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PasswordManager2Theme {
                AppNavigation()
            }
        }
    }
}