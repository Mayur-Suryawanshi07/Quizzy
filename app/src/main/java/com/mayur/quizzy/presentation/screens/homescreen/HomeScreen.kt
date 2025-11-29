package com.mayur.quizzy.presentation.screens.homescreen

import WelcomeSection
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mayur.quizzy.domain.model.CreatedQuiz
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.components.MyBottomNav
import com.mayur.quizzy.presentation.screens.components.MyFloatingAction
import com.mayur.quizzy.presentation.screens.components.MyTopAppBar
import com.mayur.quizzy.presentation.screens.createdquizzes.CreatedQuizzesViewModel
import com.mayur.quizzy.presentation.screens.technology.getTechnologyQuizzes
import com.mayur.quizzy.presentation.screens.technology.TechnologyQuizCard

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    createdQuizzesViewModel: CreatedQuizzesViewModel = viewModel()
) {
    
    val technologyQuizzes = getTechnologyQuizzes()
    val createdQuizzes by createdQuizzesViewModel.createdQuizzes.collectAsStateWithLifecycle()

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
                items(technologyQuizzes) { quiz ->
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
            Spacer(modifier = Modifier.height(24.dp))
            CreatedQuiz(
                createdQuiz = createdQuizzes.sortedByDescending { it.createdAt }.firstOrNull(),
                navController = navController
            )
        }
    }
}

