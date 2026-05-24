package com.example.passwordmanager2.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.min

class PhoneVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {

        val trimmed = text.text.take(10)

        var output = "+7"

        if (trimmed.isNotEmpty()) {
            output += " ("
        }

        for (i in trimmed.indices) {

            output += trimmed[i]

            if (i == 2) {
                output += ") "
            }

            if (i == 5) {
                output += "-"
            }

            if (i == 7) {
                output += "-"
            }
        }

        val offsetTranslator = object : OffsetMapping {

            override fun originalToTransformed(offset: Int): Int {

                val result = when {

                    offset == 0 -> {
                        if (trimmed.isEmpty()) 2 else 4
                    }

                    offset <= 3 -> offset + 4
                    offset <= 6 -> offset + 6
                    offset <= 8 -> offset + 7
                    offset <= 10 -> offset + 8
                    else -> output.length
                }

                return result.coerceAtMost(output.length)
            }

            override fun transformedToOriginal(offset: Int): Int {

                return when {

                    offset <= 4 -> 0
                    offset <= 7 -> offset - 4
                    offset <= 11 -> offset - 6
                    offset <= 14 -> offset - 7
                    offset <= 16 -> offset - 8
                    else -> 10
                }
            }
        }

        return TransformedText(
            AnnotatedString(output),
            offsetTranslator
        )
    }
}