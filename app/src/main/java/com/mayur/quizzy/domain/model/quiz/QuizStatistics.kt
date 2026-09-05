package com.mayur.quizzy.domain.model.quiz

data class QuizStatistics(
    val totalAttempts: Int,
    val totalCorrectAnswers: Int,
    val totalQuestionsAttempted: Int,
    val averageScore: Double
)

