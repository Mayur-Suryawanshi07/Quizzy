package com.mayur.quizzy.domain.use_cases

import com.mayur.quizzy.domain.model.quiz.QuizAttempt
import com.mayur.quizzy.domain.repository.QuizAttemptRepository
import javax.inject.Inject

class SaveQuizAttemptUseCase @Inject constructor(
    private val repository: QuizAttemptRepository
) {
    suspend operator fun invoke(attempt: QuizAttempt): Long = repository.insertAttempt(attempt)
}
