package com.mayur.quizzy.domain.model.quiz

data class TechnologyQuiz(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val colorHex: Long
)