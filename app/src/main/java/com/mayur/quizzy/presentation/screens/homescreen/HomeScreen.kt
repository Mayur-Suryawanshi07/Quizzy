package com.mayur.quizzy.presentation.screens.homescreen

import WelcomeSection
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.components.MyBottomNav
import com.mayur.quizzy.presentation.screens.components.MyFloatingAction
import com.mayur.quizzy.presentation.screens.components.MyTopAppBar
import com.mayur.quizzy.presentation.screens.technology.TechnologyQuizCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Quizzy",
                navigationIcon = {},

            )
        },
        bottomBar = {
            MyBottomNav(
                currentRoute = Routes.Home,
                onItemSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Routes.Home) {
                            saveState = true
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            MyFloatingAction(
                onClick = {
                    navController.navigate(Routes.ChatBot)
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            WelcomeSection()

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Technology Quizzes",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(uiState.technologyQuizzes) { quiz ->
                    TechnologyQuizCard(
                        quiz = quiz,
                        onClick = {
                            when (quiz.id) {
                                "java" -> navController.navigate(Routes.JavaQuiz)
                                "dbms" -> navController.navigate(Routes.DBMSQuiz)
                                "os" -> navController.navigate(Routes.OSQuiz)
                                "cn" -> navController.navigate(Routes.CNQuiz)
                            }
                        }
                    )
                }
            }
        }
    }
}