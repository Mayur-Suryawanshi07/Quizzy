package com.mayur.quizzy.domain.use_cases.auth

import com.mayur.quizzy.domain.repository.AuthRepository
import javax.inject.Inject

class SendPasswordResetUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> =
        authRepository.sendPasswordReset(email)
}
