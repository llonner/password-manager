package com.example.passwordmanager2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val firstName: String,

    val lastName: String,

    val phone: String,

    val avatarUri: String = ""
)