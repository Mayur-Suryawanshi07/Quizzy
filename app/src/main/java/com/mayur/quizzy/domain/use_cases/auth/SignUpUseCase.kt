package com.mayur.quizzy.domain.use_cases.auth

import com.mayur.quizzy.domain.model.auth.AuthUser
import com.mayur.quizzy.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, password: String): Result<AuthUser> =
        authRepository.signUp(name, email, password)
}
