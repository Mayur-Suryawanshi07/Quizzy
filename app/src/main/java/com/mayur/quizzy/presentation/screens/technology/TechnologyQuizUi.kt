package com.mayur.quizzy.presentation.screens.technology

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mayur.quizzy.domain.model.technology.TechnologyQuiz

data class TechnologyQuizUi(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color
)

fun TechnologyQuiz.toUi(): TechnologyQuizUi {
    return TechnologyQuizUi(
        id = id,
        title = title,
        description = description,
        icon = when (iconName) {
            "computer" -> Icons.Default.Computer
            "storage" -> Icons.Default.Storage
            "settings" -> Icons.Default.Settings
            "wifi" -> Icons.Default.Wifi
            else -> Icons.Default.Computer
        },
        color = Color(colorHex)
    )
}
