package com.mayur.quizzy.presentation.destination.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.chatbot.ChatBotScreen
import com.mayur.quizzy.presentation.screens.homescreen.HomeScreen
import com.mayur.quizzy.presentation.screens.profile.EditProfile.EditProfileScreen
import com.mayur.quizzy.presentation.screens.profile.ProfileScreen
import com.mayur.quizzy.presentation.screens.technology.TechnologyScreen
import com.mayur.quizzy.presentation.screens.technology.javaquiz.JavaQuizScreen
import com.mayur.quizzy.presentation.screens.technology.dbmsquiz.DBMSQuizScreen
import com.mayur.quizzy.presentation.screens.technology.osquiz.OSQuizScreen
import com.mayur.quizzy.presentation.screens.technology.cnquiz.CNQuizScreen
import androidx.hilt.navigation.compose.hiltViewModel


fun NavGraphBuilder.homeNavigationGraph(navController: NavHostController) {
    navigation<Graph.Main>(startDestination = Routes.Home){
        composable<Routes.Home> { backStackEntry ->
            HomeScreen(
                navController = navController,
            )
        }

        composable<Routes.Profile> {
            ProfileScreen(navController = navController)
        }

        composable<Routes.Technology> {
            TechnologyScreen(navController = navController)
        }

        composable<Routes.JavaQuiz> {
            JavaQuizScreen(navController = navController)
        }

        composable<Routes.DBMSQuiz> {
            DBMSQuizScreen(navController = navController)
        }

        composable<Routes.OSQuiz> {
            OSQuizScreen(navController = navController)
        }

        composable<Routes.CNQuiz> {
            CNQuizScreen(navController = navController)
        }

        composable<Routes.ChatBot> {
            ChatBotScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }

        composable<Routes.EditProfile> {
            EditProfileScreen(navController = navController)
        }

    }

}