package com.mayur.quizzy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mayur.quizzy.presentation.destination.AppNavigationGraph


@Composable
fun QuizzApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    AppNavigationGraph(modifier = modifier, navController)


}
