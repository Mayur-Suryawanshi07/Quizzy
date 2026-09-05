package com.mayur.quizzy.domain.model.quiz

import com.mayur.quizzy.domain.model.quiz.questions.QuestionAnswer

data class QuizResult(
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val timeTaken: Long, // in seconds
    val questionAnswers: List<QuestionAnswer> = emptyList()
)