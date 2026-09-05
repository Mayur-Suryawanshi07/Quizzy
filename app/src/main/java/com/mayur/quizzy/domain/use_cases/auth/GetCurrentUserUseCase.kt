package com.mayur.quizzy.domain.use_cases.auth

import com.mayur.quizzy.domain.model.auth.AuthUser
import com.mayur.quizzy.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): AuthUser? = authRepository.currentUser()
}
