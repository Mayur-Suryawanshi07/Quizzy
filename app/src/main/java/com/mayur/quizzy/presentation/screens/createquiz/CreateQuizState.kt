package com.mayur.quizzy.presentation.screens.createquiz

import com.mayur.quizzy.domain.model.CreatedQuestion
import com.mayur.quizzy.domain.model.QuestionType

data class CreateQuizUiState(
    val title: String = "",
    val questionType: QuestionType = QuestionType.FOUR_OPTIONS,
    val questions: List<CreatedQuestion> = emptyList(),
    val currentQuestion: CreatedQuestion = CreatedQuestion(
        question = "",
        options = listOf("", "", "", ""),
        correctAnswerIndex = -1
    ),
    val errorMessage: String? = null
)