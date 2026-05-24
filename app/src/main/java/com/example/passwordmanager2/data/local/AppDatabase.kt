package com.example.passwordmanager2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordmanager2.data.local.dao.PasswordDao
import com.example.passwordmanager2.data.local.dao.UserDao
import com.example.passwordmanager2.data.local.entity.PasswordEntity
import com.example.passwordmanager2.data.local.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
        PasswordEntity::class
    ],

    version = 5
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun passwordDao(): PasswordDao
}