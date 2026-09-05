package com.mayur.quizzy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mayur.quizzy.presentation.navigation.AppNavigationGraph


@Composable
fun QuizApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    AppNavigationGraph(modifier = modifier, navController)


}
