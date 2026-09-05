package com.mayur.quizzy.domain.repository

import com.mayur.quizzy.domain.model.profile.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {
    fun getUserProfile(userId: String): Flow<UserProfile?>
    suspend fun getUserProfileSync(userId: String): UserProfile?
    suspend fun saveUserProfile(profile: UserProfile)
}
