package com.mayur.quizzy.presentation.destination.main

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.chatbot.ChatBotScreen
import com.mayur.quizzy.presentation.screens.chatbot.ChatBotViewModel
import com.mayur.quizzy.presentation.screens.createquiz.CreateQuizScreen
import com.mayur.quizzy.presentation.screens.createquiz.CreateQuizViewModel
import com.mayur.quizzy.presentation.screens.createdquizzes.CreatedQuizzesScreen
import com.mayur.quizzy.presentation.screens.createdquizzes.CreatedQuizzesViewModel
import com.mayur.quizzy.presentation.screens.homescreen.HomeScreen
import com.mayur.quizzy.presentation.screens.profile.EditProfileScreen
import com.mayur.quizzy.presentation.screens.profile.ProfileScreen
import com.mayur.quizzy.presentation.screens.technology.TechnologyScreen
import com.mayur.quizzy.presentation.screens.technology.javaquiz.JavaQuizScreen
import com.mayur.quizzy.presentation.screens.technology.dbmsquiz.DBMSQuizScreen
import com.mayur.quizzy.presentation.screens.technology.osquiz.OSQuizScreen
import com.mayur.quizzy.presentation.screens.technology.cnquiz.CNQuizScreen


fun NavGraphBuilder.homeNavigationGraph(navController: NavHostController) {
    navigation<Graph.Main>(startDestination = Routes.Home){
        composable<Routes.Home> { backStackEntry ->
            val createdQuizzesViewModel: CreatedQuizzesViewModel = viewModel(
                viewModelStoreOwner = navController.getBackStackEntry(Graph.Main)
            )
            HomeScreen(
                navController = navController,
                createdQuizzesViewModel = createdQuizzesViewModel
            )
        }

        composable<Routes.Profile> {
            ProfileScreen(navController = navController)
        }

        composable<Routes.Updates> { backStackEntry ->
            CreatedQuizzesScreen(navController = navController)
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

        composable<Routes.CreateQuiz> { backStackEntry ->
            val createdQuizzesViewModel: CreatedQuizzesViewModel = viewModel(
                viewModelStoreOwner = navController.getBackStackEntry(Graph.Main)
            )
            CreateQuizScreen(
                navController = navController,
                createdQuizzesViewModel = createdQuizzesViewModel,
                viewModel = viewModel { CreateQuizViewModel(createdQuizzesViewModel) }
            )
        }
        composable<Routes.CreatedQuizzes> { backStackEntry ->
            val createdQuizzesViewModel: CreatedQuizzesViewModel = viewModel(
                viewModelStoreOwner = navController.getBackStackEntry(Graph.Main)
            )
            CreatedQuizzesScreen(
                navController = navController,
                viewModel = createdQuizzesViewModel
            )
        }

        composable<Routes.ChatBot> {
            ChatBotScreen(
                viewModel = viewModel(),
                navController = navController
            )
        }

        composable<Routes.EditProfile> {
            EditProfileScreen(navController = navController)
        }

    }

}