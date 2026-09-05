package com.mayur.quizzy.domain.use_cases.auth

import com.mayur.quizzy.domain.repository.AuthRepository
import com.mayur.quizzy.domain.repository.QuizAttemptRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val quizAttemptRepository: QuizAttemptRepository
) {
    suspend operator fun invoke() {
        quizAttemptRepository.deleteAllAttempts()
        authRepository.signOut()
    }
}
