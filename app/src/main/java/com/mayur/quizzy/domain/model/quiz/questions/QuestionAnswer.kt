package com.mayur.quizzy.domain.model.quiz.questions

data class QuestionAnswer(
    val question: Question,
    val userAnswerIndex: Int?, // null if not answered
    val isCorrect: Boolean
)