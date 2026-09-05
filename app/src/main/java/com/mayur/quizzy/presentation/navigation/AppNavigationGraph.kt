package com.mayur.quizzy.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mayur.quizzy.presentation.navigation.auth.authNavigationGraph
import com.mayur.quizzy.presentation.navigation.main.homeNavigationGraph

@Composable
fun AppNavigationGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Graph.Auth,
        modifier = modifier.background(MaterialTheme.colorScheme.background)
    ) {
        homeNavigationGraph(navController = navController)
        authNavigationGraph(navController = navController)
    }

}