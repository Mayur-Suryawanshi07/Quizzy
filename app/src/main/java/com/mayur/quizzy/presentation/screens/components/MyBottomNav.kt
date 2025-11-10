package com.mayur.quizzy.presentation.screens.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.mayur.quizzy.presentation.navigation.Routes

@Composable
fun MyBottomNav(
    currentRoute: Routes,
    onItemSelected: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavigationItem(Icons.Filled.Home, Icons.Filled.Home, "Home", Routes.Home),
        NavigationItem(Icons.Filled.Person, Icons.Filled.Person, "Profile", Routes.Profile),
        NavigationItem(Icons.Filled.Update, Icons.Filled.Update, "Updates", Routes.Updates)
    )
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            val isSelected = currentRoute::class == item.route::class
            NavigationBarItem(
                selected = isSelected,
                onClick = { onItemSelected(item.route) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedItems else item.unselectedItems,
                        contentDescription = item.name
                    )
                },
                label = { Text(item.name) }
            )
        }
    }
}

data class NavigationItem(
    val selectedItems: ImageVector,
    val unselectedItems: ImageVector,
    val name: String,
    val route: Routes
)