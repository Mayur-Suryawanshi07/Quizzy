package com.mayur.quizzy.presentation.screens.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyFloatingAction(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Message,
            contentDescription = "AiChatbot",
            modifier = Modifier.size(30.dp)
        )
    }


}

