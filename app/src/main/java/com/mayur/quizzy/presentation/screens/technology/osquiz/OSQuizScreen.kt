package com.mayur.quizzy.presentation.screens.technology.osquiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mayur.quizzy.presentation.screens.component.MyTopAppBar
import com.mayur.quizzy.presentation.screens.technology.javaquiz.AnswerOption
import com.mayur.quizzy.presentation.screens.technology.javaquiz.QuizResultScreen
import com.mayur.quizzy.presentation.screens.technology.javaquiz.QuizSettingsDialog

@Composable
fun OSQuizScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: OSQuizViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "OS Quiz",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.showSettingsDialog -> {
                    QuizSettingsDialog(
                        numberOfQuestions = uiState.selectedQuestionCount,
                        timerPerQuestion = uiState.selectedTimerPerQuestion,
                        onNumberOfQuestionsChange = { viewModel.updateNumberOfQuestions(it) },
                        onTimerPerQuestionChange = { viewModel.updateTimerPerQuestion(it) },
                        onProceed = { viewModel.startQuiz() },
                        onDismiss = { navController.popBackStack() }
                    )
                }
                uiState.showResult -> {
                    uiState.quizResult?.let { result ->
                        QuizResultScreen(
                            result = result,
                            onRetry = {
                                viewModel.resetQuiz()
                                viewModel.showSettingsDialog()
                            },
                            onBack = { navController.popBackStack() }
                        )
                    }
                }
                uiState.isQuizStarted -> {
                    OSQuizContent(
                        uiState = uiState,
                        onAnswerSelected = { viewModel.selectAnswer(it) },
                        onNext = { viewModel.nextQuestion() },
                        onPrevious = { viewModel.previousQuestion() },
                        onFinish = { viewModel.finishQuiz() }
                    )
                }
            }
        }
    }
}

@Composable
fun OSQuizContent(
    uiState: OSQuizUiState,
    onAnswerSelected: (Int) -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onFinish: () -> Unit
) {
    if (uiState.questions.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No questions available")
        }
        return
    }
    
    val currentQuestion = uiState.questions.getOrNull(uiState.currentQuestionIndex)
    val progress = if (uiState.questions.isNotEmpty()) {
        (uiState.currentQuestionIndex + 1).toFloat() / uiState.questions.size.toFloat()
    } else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(300),
        label = "progress"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Progress Bar
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${uiState.currentQuestionIndex + 1} of ${uiState.questions.size}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "${uiState.timeRemaining}s",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = if (uiState.timeRemaining <= 10) Color.Red else MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Question Card
        currentQuestion?.let { question ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Answer Options
                    question.options.forEachIndexed { index, option ->
                        AnswerOption(
                            text = option,
                            isSelected = uiState.selectedAnswer == index,
                            onClick = { onAnswerSelected(index) }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Navigation Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onPrevious,
                modifier = Modifier.weight(1f),
                enabled = uiState.currentQuestionIndex > 0
            ) {
                Text("Previous")
            }

            if (uiState.currentQuestionIndex < uiState.questions.size - 1) {
                Button(
                    onClick = onNext,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Next")
                }
            } else {
                Button(
                    onClick = onFinish,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Finish Quiz")
                }
            }
        }
    }
}

