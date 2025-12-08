package com.mayur.quizzy.presentation.destination.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mayur.quizzy.presentation.screens.auth.login.LoginScreen
import com.mayur.quizzy.presentation.screens.auth.login.forgetpass.ForgetPasswordScreen
import com.mayur.quizzy.presentation.screens.auth.signup.SignUpScreen
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes

fun NavGraphBuilder.authNavigationGraph( navController: NavHostController) {
    navigation<Graph.Auth>(startDestination = Routes.Login){
        composable<Routes.Login> {
            LoginScreen(navController = navController)
        }
        composable<Routes.SignUp> {
            SignUpScreen(navController = navController)
        }
        composable<Routes.ForgetPassword> {
            ForgetPasswordScreen(navController = navController)
        }

    }
}