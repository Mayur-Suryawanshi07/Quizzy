package com.mayur.quizzy.presentation.screens.profile

data class ProfileUiState(
    val totalAttempts: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val totalQuestionsAttempted: Int = 0,
    val averageScore: Double = 0.0,
    val profileName: String = "",
    val profileDescription: String = ""
)