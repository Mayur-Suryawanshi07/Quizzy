package com.mayur.quizzy.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.component.MyBottomNav
import com.mayur.quizzy.presentation.screens.component.MyTopAppBar

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val stats = listOf(
        StatItem(
            "Quizzes Taken",
            uiState.totalAttempts.toString(),
            Icons.Default.QuestionAnswer,
            Color(0xFF2196F3)
        ),
        StatItem(
            "Correct Answers",
            uiState.totalCorrectAnswers.toString(),
            Icons.Default.CheckCircle,
            Color(0xFF4CAF50)
        ),
        StatItem(
            "Questions Attempted",
            uiState.totalQuestionsAttempted.toString(),
            Icons.Default.Help,
            Color(0xFF9C27B0)
        ),
        StatItem(
            "Average Score",
            String.format("%.1f%%", uiState.averageScore),
            Icons.Default.EmojiEvents,
            Color(0xFFFFD700)
        )
    )

    val onSignOut = {
        viewModel.signOut {
            navController.navigate(Graph.Auth) {
                popUpTo(Graph.Main) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    val profileOptions = profileOptions(navController, onSignOut)

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Profile",
                navigationIcon = {},

                )
        },
        bottomBar = {
            MyBottomNav(
                currentRoute = Routes.Profile,
                onItemSelected = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(Graph.Main) {
                            saveState = true
                            inclusive = false
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            item {
                // Profile Header
                ProfileHeader(
                    profileName = uiState.profileName,
                    profileDescription = uiState.profileDescription
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Statistics Section
                StatisticsSection(stats = stats)

                Spacer(modifier = Modifier.height(24.dp))

                // Profile Options
                Text(
                    text = "Options",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column {
                        profileOptions.forEachIndexed { index, option ->
                            ProfileOptionItem(
                                option = option,
                                showDivider = index < profileOptions.size - 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

data class StatItem(
    val label: String,
    val value: String,
    val icon: ImageVector,
    val color: Color
)
