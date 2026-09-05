package com.mayur.quizzy.data.model.quiz

// Projection returned by Room when quiz attempts are grouped by quiz ID.
data class QuizAttemptCount(
    val quizId: String,
    val count: Int
)

// Projection returned by Room for total attempted questions per quiz.
data class QuizQuestionsCount(
    val quizId: String,
    val total: Int
)
