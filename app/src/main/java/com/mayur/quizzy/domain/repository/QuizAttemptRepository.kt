package com.mayur.quizzy.domain.repository

import com.mayur.quizzy.domain.model.quiz.QuizAttempt
import com.mayur.quizzy.domain.model.quiz.QuizStatistics
import kotlinx.coroutines.flow.Flow

interface QuizAttemptRepository {
    suspend fun insertAttempt(attempt: QuizAttempt): Long
    suspend fun deleteAllAttempts()
    fun getQuizStatistics(): Flow<QuizStatistics>
}
