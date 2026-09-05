package com.mayur.quizzy.domain.model.quiz.questions

data class Question(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String? = null
)