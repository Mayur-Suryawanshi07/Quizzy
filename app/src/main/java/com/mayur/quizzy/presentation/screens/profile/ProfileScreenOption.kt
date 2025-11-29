import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.screens.profile.StatItem

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
        ProfileOption("Edit Profile", Icons.Default.Edit) {
            navController.navigate(com.mayur.quizzy.presentation.navigation.Routes.EditProfile)
        },
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
data class ProfileOption(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit
)