package com.example.passwordmanager2.data.session

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "session"
)

class SessionManager(
    private val context: Context
) {

    companion object {

        val USER_PHONE_KEY =
            stringPreferencesKey("user_phone")

        val PIN_CODE_KEY =
            stringPreferencesKey("pin_code")

        val PIN_ENABLED_KEY =
            booleanPreferencesKey("pin_enabled")
    }

    // ТЕКУЩИЙ ПОЛЬЗОВАТЕЛЬ

    val currentUserPhone: Flow<String?> =
        context.dataStore.data.map {

            it[USER_PHONE_KEY]
        }

    suspend fun saveUserPhone(
        phone: String
    ) {

        context.dataStore.edit {

            it[USER_PHONE_KEY] = phone
        }
    }

    suspend fun clearSession() {

        context.dataStore.edit {

            it.remove(USER_PHONE_KEY)
        }
    }

    // PIN-КОД

    suspend fun savePin(
        pin: String
    ) {

        context.dataStore.edit {

            it[PIN_CODE_KEY] = pin
            it[PIN_ENABLED_KEY] = true
        }
    }

    val currentPin: Flow<String?> =
        context.dataStore.data.map {

            it[PIN_CODE_KEY]
        }

    val isPinEnabled: Flow<Boolean> =
        context.dataStore.data.map {

            it[PIN_ENABLED_KEY] ?: false
        }

    suspend fun clearPin() {

        context.dataStore.edit {

            it.remove(PIN_CODE_KEY)
            it[PIN_ENABLED_KEY] = false
        }
    }
}