package com.mayur.quizzy.domain.model.user

data class UserProfile(
    val userId: String = "default_user",
    val name: String = "",
    val description: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

