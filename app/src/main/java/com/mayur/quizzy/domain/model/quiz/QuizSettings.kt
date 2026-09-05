package com.mayur.quizzy.domain.model.quiz

data class QuizSettings(
    val numberOfQuestions: Int,
    val timerPerQuestion: Int // in seconds
)