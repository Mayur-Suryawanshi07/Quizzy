package com.mayur.quizzy.presentation.screens.technology.javaquiz

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mayur.quizzy.domain.model.quiz.questions.QuestionAnswer
import com.mayur.quizzy.domain.model.quiz.QuizResult
import com.mayur.quizzy.presentation.screens.component.MyTopAppBar

@Composable
fun JavaQuizScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: JavaQuizViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            MyTopAppBar(
                title = "Java Quiz",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
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
                    QuizContent(
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
fun QuizSettingsDialog(
    numberOfQuestions: Int,
    timerPerQuestion: Int,
    onNumberOfQuestionsChange: (Int) -> Unit,
    onTimerPerQuestionChange: (Int) -> Unit,
    onProceed: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Quiz Settings",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Number of Questions
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Number of Questions (Max 10)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        (1..10).forEach { count ->
                            FilterChip(
                                selected = numberOfQuestions == count,
                                onClick = { onNumberOfQuestionsChange(count) },
                                label = { Text("$count") }
                            )
                        }
                    }
                }

                // Timer per Question
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Timer per Question (seconds)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(15, 30, 45, 60).forEach { seconds ->
                            FilterChip(
                                selected = timerPerQuestion == seconds,
                                onClick = { onTimerPerQuestionChange(seconds) },
                                label = { Text("$seconds") },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Proceed Button
                Button(
                    onClick = onProceed,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Start Quiz",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun QuizContent(
    uiState: JavaQuizUiState,
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

@Composable
fun AnswerOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        } else null
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun QuizResultScreen(
    result: QuizResult,
    onRetry: () -> Unit,
    onBack: () -> Unit
) {
    val percentage = if (result.totalQuestions > 0) {
        (result.correctAnswers.toFloat() / result.totalQuestions.toFloat()) * 100f
    } else 0f

    val correctAnswers = result.questionAnswers.filter { it.isCorrect }
    val wrongAnswers = result.questionAnswers.filter { !it.isCorrect }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        // Header Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Result Icon
            Icon(
                imageVector = if (percentage >= 70) Icons.Default.CheckCircle else Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = if (percentage >= 70) Color(0xFF4CAF50) else Color(0xFFF44336)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = if (percentage >= 70) "Congratulations!" else "Keep Practicing!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "You scored ${String.format("%.1f", percentage)}%",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Result Stats Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ResultStatItem(
                        label = "Total Questions",
                        value = result.totalQuestions.toString(),
                        color = MaterialTheme.colorScheme.primary
                    )
                    ResultStatItem(
                        label = "Correct Answers",
                        value = result.correctAnswers.toString(),
                        color = Color(0xFF4CAF50)
                    )
                    ResultStatItem(
                        label = "Wrong Answers",
                        value = result.wrongAnswers.toString(),
                        color = Color(0xFFF44336)
                    )
                    ResultStatItem(
                        label = "Time Taken",
                        value = "${result.timeTaken}s",
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }

        // Questions List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Correct Answers Section
            if (correctAnswers.isNotEmpty()) {
                item {
                    Text(
                        text = "Correct Answers (${correctAnswers.size})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(correctAnswers.size) { index ->
                    QuestionAnswerItem(
                        questionAnswer = correctAnswers[index],
                        questionNumber = result.questionAnswers.indexOf(correctAnswers[index]) + 1
                    )
                }
            }

            // Wrong Answers Section
            if (wrongAnswers.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Wrong Answers (${wrongAnswers.size})",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF44336),
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                items(wrongAnswers.size) { index ->
                    QuestionAnswerItem(
                        questionAnswer = wrongAnswers[index],
                        questionNumber = result.questionAnswers.indexOf(wrongAnswers[index]) + 1
                    )
                }
            }
        }

        // Action Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }
            Button(
                onClick = onRetry,
                modifier = Modifier.weight(1f)
            ) {
                Text("Retry Quiz")
            }
        }
    }
}

@Composable
fun ResultStatItem(
    label: String,
    value: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
fun QuestionAnswerItem(
    questionAnswer: QuestionAnswer,
    questionNumber: Int
) {
    val question = questionAnswer.question
    val isCorrect = questionAnswer.isCorrect
    val userAnswerIndex = questionAnswer.userAnswerIndex

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) {
                Color(0xFF4CAF50).copy(alpha = 0.1f)
            } else {
                Color(0xFFF44336).copy(alpha = 0.1f)
            }
        ),
        border = androidx.compose.foundation.BorderStroke(
            2.dp,
            if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Question Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Q$questionNumber",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336)
                )
                Icon(
                    imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                    contentDescription = if (isCorrect) "Correct" else "Wrong",
                    tint = if (isCorrect) Color(0xFF4CAF50) else Color(0xFFF44336),
                    modifier = Modifier.size(24.dp)
                )
            }

            // Question Text
            Text(
                text = question.question,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Options
            question.options.forEachIndexed { index, option ->
                val isCorrectAnswer = index == question.correctAnswerIndex
                val isUserAnswer = index == userAnswerIndex
                val isWrongUserAnswer = isUserAnswer && !isCorrect

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            isCorrectAnswer -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                            isWrongUserAnswer -> Color(0xFFF44336).copy(alpha = 0.2f)
                            else -> MaterialTheme.colorScheme.surface
                        }
                    ),
                    border = when {
                        isCorrectAnswer -> androidx.compose.foundation.BorderStroke(2.dp, Color(0xFF4CAF50))
                        isWrongUserAnswer -> androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFF44336))
                        else -> null
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        if (isCorrectAnswer) {
                            Text(
                                text = "✓ Correct",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF4CAF50),
                                fontWeight = FontWeight.Bold
                            )
                        } else if (isWrongUserAnswer) {
                            Text(
                                text = "✗ Your Answer",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFF44336),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Explanation if available
            question.explanation?.let { explanation ->
                Spacer(modifier = Modifier.height(4.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
                    )
                ) {
                    Text(
                        text = "Explanation: $explanation",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

