package com.mayur.quizzy.presentation.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.components.MyBottomNav
import com.mayur.quizzy.presentation.screens.components.MyTopAppBar

data class StatItem(
    val label: String,
    val value: String,
    val icon: ImageVector,
    val color: Color
)

data class ProfileOption(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun ProfileScreen(modifier: Modifier = Modifier,navController: NavHostController) {


    val stats = statsItems()



    val profileOptions = profileOptions(navController)

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
                        popUpTo(Routes.Home) { inclusive = false }
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
            ProfileHeader()
            
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

@Composable
fun ProfileOptionItem(option: ProfileOption, showDivider: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { option.onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = option.icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = option.title,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
    
    if (showDivider) {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
        )
    }
}

fun statsItems(): List<StatItem> {
    val stats = listOf(
        StatItem("Quizzes Taken", "42", Icons.Default.QuestionAnswer, Color(0xFF2196F3)),
        StatItem("Correct Answers", "315", Icons.Default.CheckCircle, Color(0xFF4CAF50)),
        StatItem("Total Score", "8,420", Icons.Default.EmojiEvents, Color(0xFFFFD700)),
        StatItem("Best Streak", "7 days", Icons.Default.LocalFireDepartment, Color(0xFFFF5722))
    )
    return stats
}

fun profileOptions(navController: NavHostController): List<ProfileOption> {
    val profileOptions = listOf(
        ProfileOption("Edit Profile", Icons.Default.Edit) { },
        ProfileOption("Settings", Icons.Default.Settings) { },
        ProfileOption("Help & Support", Icons.Default.Help) { },
        ProfileOption("About", Icons.Default.Info) { },
        ProfileOption("Sign out", Icons.Default.Logout) {
            FirebaseAuth.getInstance().signOut()
            navController.navigate(Graph.Auth) {
                popUpTo(Graph.Main) { inclusive = true }
                launchSingleTop = true
            }
        }
    )
    return profileOptions
}