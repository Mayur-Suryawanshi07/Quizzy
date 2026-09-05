package com.mayur.quizzy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val userId: String = "default_user", // Using Firebase UID or default
    val name: String = "",
    val description: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

