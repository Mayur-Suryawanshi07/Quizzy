package com.mayur.quizzy.presentation.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MyBottomNav(
    currentRoute: String,
    onItemSelected: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavigationItem(Icons.Default.Home, Icons.Default.Home, "Home", "home"),
        NavigationItem(Icons.Default.Person, Icons.Default.Person, "Profile", "profile"),
        NavigationItem(Icons.Default.Settings, Icons.Default.Settings, "Settings", "settings")
    )
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onItemSelected(item) },
                icon = {
                    androidx.compose.material3.Icon(
                        imageVector = if (currentRoute == item.route) item.selectedItems else item.unselectedItems,
                        contentDescription = item.name
                    )
                },
                label = { androidx.compose.material3.Text(item.name) }
            )
        }
    }
}

data class NavigationItem(
    val selectedItems: ImageVector,
    val unselectedItems: ImageVector,
    val name: String,
    val route: String
)