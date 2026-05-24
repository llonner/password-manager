package com.example.passwordmanager2.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.passwordmanager2.data.local.entity.UserEntity
import androidx.room.Update

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE phone = :phone LIMIT 1")
    suspend fun getUserByPhone(
        phone: String
    ): UserEntity?
    @Update
    suspend fun updateUser(user: UserEntity)
}