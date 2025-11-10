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
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.screens.components.MyBottomNav
import com.mayur.quizzy.presentation.screens.components.MyTopAppBar

data class StatItem(
    val label: String,
    val value: String,
    val icon: ImageVector,
    val color: Color
)

data class Achievement(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val isUnlocked: Boolean
)

data class ProfileOption(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)

@Composable
fun ProfileScreen(modifier: Modifier = Modifier,navController: NavHostController) {
    val stats = listOf(
        StatItem("Quizzes Taken", "42", Icons.Default.QuestionAnswer, Color(0xFF2196F3)),
        StatItem("Correct Answers", "315", Icons.Default.CheckCircle, Color(0xFF4CAF50)),
        StatItem("Total Score", "8,420", Icons.Default.EmojiEvents, Color(0xFFFFD700)),
        StatItem("Best Streak", "7 days", Icons.Default.LocalFireDepartment, Color(0xFFFF5722))
    )

    val achievements = listOf(
        Achievement("First Quiz", "Complete your first quiz", Icons.Default.Star, Color(0xFFFFD700), true),
        Achievement("Perfect Score", "Score 100% on any quiz", Icons.Default.EmojiEvents, Color(0xFFFF9800), true),
        Achievement("Week Warrior", "Complete 7 quizzes in a week", Icons.Default.CalendarToday, Color(0xFF9C27B0), false),
        Achievement("Speed Demon", "Complete a quiz in under 2 minutes", Icons.Default.Timer, Color(0xFF00BCD4), false)
    )

    val profileOptions = listOf(
        ProfileOption("Edit Profile", Icons.Default.Edit) { },
        ProfileOption("Quiz History", Icons.Default.History) { },
        ProfileOption("Achievements", Icons.Default.EmojiEvents) { },
        ProfileOption("Settings", Icons.Default.Settings) { },
        ProfileOption("Help & Support", Icons.Default.Help) { },
        ProfileOption("About", Icons.Default.Info) { },
        ProfileOption("Sign out", Icons.Default.Logout) {
            FirebaseAuth.getInstance().signOut()
            navController.navigate(com.mayur.quizzy.presentation.navigation.Graph.Auth) {
                popUpTo(com.mayur.quizzy.presentation.navigation.Graph.Main) { inclusive = true }
                launchSingleTop = true
            }
        }
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Profile",
                navigationIcon = {},
                actions = {}
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
            
            // Achievements Section
            Text(
                text = "Achievements",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                achievements.forEach { achievement ->
                    AchievementCard(achievement = achievement)
                }
            }
            
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
fun ProfileHeader() {
    val user = remember { FirebaseAuth.getInstance().currentUser }
    val displayName = user?.displayName ?: user?.email ?: "Guest"
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = displayName ?: "Guest",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = "Quiz Master",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Level Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Level 12",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun StatisticsSection(stats: List<StatItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            stats.forEach { stat ->
                StatCard(stat = stat, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun StatCard(stat: StatItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = stat.icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = stat.color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stat.value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stat.label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun AchievementCard(achievement: Achievement) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (achievement.isUnlocked) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)
            }
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (achievement.isUnlocked) 2.dp else 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        if (achievement.isUnlocked) {
                            achievement.color.copy(alpha = 0.2f)
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = achievement.icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (achievement.isUnlocked) {
                        achievement.color
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    }
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = achievement.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (achievement.isUnlocked) {
                        MaterialTheme.colorScheme.onSurface
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    }
                )
                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (achievement.isUnlocked) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    }
                )
            }
            
            if (achievement.isUnlocked) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Unlocked",
                    tint = Color(0xFF4CAF50),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                    modifier = Modifier.size(24.dp)
                )
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

