package com.example.passwordmanager2.data.local.dao

import androidx.room.*
import com.example.passwordmanager2.data.local.entity.PasswordEntity
import kotlinx.coroutines.flow.Flow
import androidx.room.Update

@Dao
interface PasswordDao {

    @Insert
    suspend fun insertPassword(password: PasswordEntity)

    @Delete
    suspend fun deletePassword(password: PasswordEntity)

    @Query("SELECT * FROM passwords WHERE userPhone = :phone")
    fun getPasswordsByPhone(
        phone: String
    ): Flow<List<PasswordEntity>>


    @Query("SELECT * FROM passwords WHERE id = :id LIMIT 1")
    suspend fun getPasswordById(id: Int): PasswordEntity?
    @Update
    suspend fun updatePassword(
        password: PasswordEntity
    )
    @Query("SELECT * FROM passwords WHERE userPhone = :phone")
    suspend fun getPasswordsByPhoneOnce(
        phone: String
    ): List<PasswordEntity>
    @Query("SELECT * FROM passwords WHERE userPhone = :phone")
    suspend fun getPasswordsListByPhone(
        phone: String
    ): List<PasswordEntity>
    @Query("DELETE FROM passwords")
    suspend fun deleteAllPasswords()
}