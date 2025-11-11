package com.mayur.quizzy.presentation.screens.createscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.components.MyBottomNav
import com.mayur.quizzy.presentation.screens.components.MyTopAppBar

@Composable
fun CreateScreen(modifier: Modifier = Modifier,navController: NavHostController) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Updates",
                navigationIcon = {},

            )
        },
        bottomBar = {
            MyBottomNav(
                currentRoute = Routes.Updates,
                onItemSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Routes.Home) { inclusive = false }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ){
            Text(text = "Update Screen")
        }
    }
}