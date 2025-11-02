package com.mayur.quizzy.presentation.screens.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.IconButton


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(text = title)
        },
        navigationIcon = {
            IconButton(
                onClick = {

                },

            ) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")

            }
        },
        actions = {
            IconButton(
                onClick = {

                },

                ) {
                Icon(imageVector = Icons.Default.Notifications, contentDescription = "menu")

            }


        }

    )

}