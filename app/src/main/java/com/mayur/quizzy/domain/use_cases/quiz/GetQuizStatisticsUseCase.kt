package com.mayur.quizzy.domain.use_cases.quiz

import com.mayur.quizzy.domain.model.quiz.QuizStatistics
import com.mayur.quizzy.domain.repository.QuizAttemptRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuizStatisticsUseCase @Inject constructor(
    private val repository: QuizAttemptRepository
) {
    operator fun invoke(): Flow<QuizStatistics> = repository.getQuizStatistics()
}
