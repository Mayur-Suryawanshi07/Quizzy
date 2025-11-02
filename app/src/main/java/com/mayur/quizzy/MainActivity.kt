package com.mayur.quizzy

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.tooling.preview.Preview
import com.mayur.quizzy.presentation.screens.components.MyTopAppBar
import com.mayur.quizzy.ui.theme.QuizzyTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.mayur.quizzy.presentation.screens.components.MyBottomNav
import com.mayur.quizzy.presentation.screens.components.MyAppDrawer
import com.mayur.quizzy.presentation.screens.homescreen.HomeScreen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizzyTheme {
                var currentRoute by remember { mutableStateOf("home") }
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                ModalNavigationDrawer(
                    drawerContent = {
                        MyAppDrawer(
                            currentRoute = currentRoute,
                            onItemSelected = {
                                currentRoute = it.route
                                scope.launch { drawerState.close() }
                            }
                        )
                    },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = {
                            MyTopAppBar(
                                title = currentRoute.replaceFirstChar { it.uppercase() },
                                navigationIcon = {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(Icons.Default.Menu, contentDescription = "Menu")
                                    }
                                },
                                actions = {}
                            )
                        },
                        bottomBar = {
                            MyBottomNav(
                                currentRoute = currentRoute,
                                onItemSelected = { currentRoute = it.route }
                            )
                        }
                    ) { innerPadding ->
                        when (currentRoute) {
                            "home" -> HomeScreen(Modifier.padding(innerPadding))
                            "profile" -> Text("Profile Screen", Modifier.padding(innerPadding))
                            "settings" -> Text("Settings Screen", Modifier.padding(innerPadding))
                            else -> Text("Not Found", Modifier.padding(innerPadding))
                        }
                    }
                }
            }
        }
    }
}


@Preview(name = "Light Mode",showBackground = true,  uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode",showBackground = true,  uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class PreviewMode


