package com.example.passwordmanager2.security

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

object EncryptionManager {

    private const val SECRET_KEY =
        "1234567890123456"

    private val key =
        SecretKeySpec(
            SECRET_KEY.toByteArray(),
            "AES"
        )

    fun encrypt(
        text: String
    ): String {

        val cipher =
            Cipher.getInstance("AES")

        cipher.init(
            Cipher.ENCRYPT_MODE,
            key
        )

        val encrypted =
            cipher.doFinal(text.toByteArray())

        return Base64.encodeToString(
            encrypted,
            Base64.DEFAULT
        )
    }

    fun decrypt(
        text: String
    ): String {

        val cipher =
            Cipher.getInstance("AES")

        cipher.init(
            Cipher.DECRYPT_MODE,
            key
        )

        val decoded =
            Base64.decode(
                text,
                Base64.DEFAULT
            )

        return String(
            cipher.doFinal(decoded)
        )
    }
}