package com.mayur.quizzy.presentation.destination.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.quizz.ui.login.LoginScreen
import com.example.quizz.ui.signup.SignUpScreen
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.homescreen.HomeScreen

fun NavGraphBuilder.authNavigationGraph( navController: NavHostController) {
    navigation<Graph.Auth>(startDestination = Routes.Login){
        composable<Routes.Login> {
            LoginScreen(navController = navController)
        }
        composable<Routes.SignUp> {
            SignUpScreen(navController = navController)
        }

    }
}