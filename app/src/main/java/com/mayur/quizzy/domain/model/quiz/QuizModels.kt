package com.mayur.quizzy.domain.model.quiz

import com.mayur.quizzy.domain.model.quiz.questions.Question

data class QuizModels(
    val id: String,
    val title: String,
    val description: String,
    val category: String,
    val questions: List<Question>
)





