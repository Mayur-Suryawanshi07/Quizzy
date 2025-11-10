package com.mayur.quizzy.presentation.screens.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mayur.quizzy.presentation.navigation.Routes

@Composable
fun QuizzApp(
    currentRoute: Routes,
    onItemSelected: (Routes) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        NavigationItem(Icons.Default.Home, Icons.Default.Home, "Home", Routes.Home),
        NavigationItem(Icons.Default.Person, Icons.Default.Person, "Profile", Routes.Profile),
        NavigationItem(Icons.Default.Update, Icons.Default.Update, "Updates", Routes.Updates)
    )
    ModalDrawerSheet(modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Quizzy", modifier = Modifier.padding(bottom = 24.dp))
            items.forEach { item ->
                NavigationDrawerItem(
                    label = { Text(item.name) },
                    selected = currentRoute::class == item.route::class,
                    onClick = { onItemSelected(item.route) },
                    icon = {
                        androidx.compose.material3.Icon(
                            imageVector = item.selectedItems,
                            contentDescription = item.name
                        )
                    },
                    modifier = Modifier.padding(vertical = 4.dp),
                    colors = NavigationDrawerItemDefaults.colors()
                )
            }
        }
    }
}