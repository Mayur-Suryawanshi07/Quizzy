package com.mayur.quizzy.domain.repository

import com.mayur.quizzy.domain.model.auth.AuthUser

interface AuthRepository {
    fun currentUser(): AuthUser?
    suspend fun signIn(email: String, password: String): Result<AuthUser>
    suspend fun signUp(name: String, email: String, password: String): Result<AuthUser>
    suspend fun sendPasswordReset(email: String): Result<Unit>
    fun signOut()
}
