package com.mayur.quizzy.presentation.destination

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mayur.quizzy.presentation.destination.auth.authNavigationGraph
import com.mayur.quizzy.presentation.destination.main.homeNavigationGraph
import com.mayur.quizzy.presentation.navigation.Graph

@Composable
fun AppNavigationGraph(modifier: Modifier = Modifier, navController: NavHostController) {

    NavHost(navController = navController, startDestination = Graph.Auth, modifier = modifier )
    {
        homeNavigationGraph(navController = navController)
        authNavigationGraph(navController = navController)
    }


}