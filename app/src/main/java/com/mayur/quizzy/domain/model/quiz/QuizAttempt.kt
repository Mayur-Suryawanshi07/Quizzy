package com.mayur.quizzy.domain.model.quiz

data class QuizAttempt(
    val id: Long = 0,
    val quizId: String,
    val quizName: String,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val timeTaken: Long, // in seconds
    val score: Int, // percentage score
    val completedAt: Long = System.currentTimeMillis()
)

