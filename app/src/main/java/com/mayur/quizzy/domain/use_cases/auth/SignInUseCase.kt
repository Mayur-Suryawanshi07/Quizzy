package com.mayur.quizzy.domain.use_cases.auth

import com.mayur.quizzy.domain.model.auth.AuthUser
import com.mayur.quizzy.domain.repository.AuthRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<AuthUser> =
        authRepository.signIn(email, password)
}
