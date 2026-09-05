package com.mayur.quizzy.data.repository

import com.mayur.quizzy.data.local.dao.QuizAttemptDao
import com.mayur.quizzy.data.mapper.toQuizAttemptEntity
import com.mayur.quizzy.domain.model.quiz.QuizAttempt
import com.mayur.quizzy.domain.model.quiz.QuizStatistics
import com.mayur.quizzy.domain.repository.QuizAttemptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class QuizAttemptRepositoryImpl @Inject constructor(
    private val quizAttemptDao: QuizAttemptDao
) : QuizAttemptRepository {

    override suspend fun insertAttempt(attempt: QuizAttempt): Long =
        quizAttemptDao.insertAttempt(attempt.toQuizAttemptEntity())

    override suspend fun deleteAllAttempts() =
        quizAttemptDao.deleteAllAttempts()

    override fun getQuizStatistics(): Flow<QuizStatistics> = combine(
        quizAttemptDao.getTotalAttemptsCount(),
        quizAttemptDao.getTotalCorrectAnswers(),
        quizAttemptDao.getTotalQuestionsAttempted(),
        quizAttemptDao.getAverageScore()
    ) { totalAttempts, totalCorrect, totalQuestions, avgScore ->
        QuizStatistics(
            totalAttempts = totalAttempts,
            totalCorrectAnswers = totalCorrect ?: 0,
            totalQuestionsAttempted = totalQuestions ?: 0,
            averageScore = avgScore ?: 0.0
        )
    }
}
