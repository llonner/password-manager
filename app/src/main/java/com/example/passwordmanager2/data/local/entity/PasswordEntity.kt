package com.example.passwordmanager2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "passwords"
)
data class PasswordEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val login: String,

    val password: String,

    val website: String,

    val userPhone: String,

    val category: String = "Другое",

    val isFavorite: Boolean = false
)