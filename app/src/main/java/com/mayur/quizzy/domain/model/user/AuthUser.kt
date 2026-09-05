package com.mayur.quizzy.domain.model.user

data class AuthUser(
    val id: String,
    val email: String?,
    val displayName: String?
)
