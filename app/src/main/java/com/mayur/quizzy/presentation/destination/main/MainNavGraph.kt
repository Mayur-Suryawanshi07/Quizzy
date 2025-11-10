package com.mayur.quizzy.presentation.destination.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.updatescreen.UpdateScreen
import com.mayur.quizzy.presentation.screens.homescreen.HomeScreen
import com.mayur.quizzy.presentation.screens.profile.ProfileScreen

fun NavGraphBuilder.homeNavigationGraph(navController: NavHostController) {

    navigation<Graph.Main>(startDestination = Routes.Home){
        composable<Routes.Home> {
            HomeScreen(navController = navController)
        }


        composable<Routes.Profile> {
            ProfileScreen(navController = navController)
        }

        composable<Routes.Updates> { backStackEntry ->
            UpdateScreen(navController = navController)
        }

    }

}