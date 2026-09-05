package com.mayur.quizzy.domain.use_cases

import com.mayur.quizzy.domain.model.user.UserProfile
import com.mayur.quizzy.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    fun observe(userId: String): Flow<UserProfile?> = repository.getUserProfile(userId)

    suspend fun current(userId: String): UserProfile? = repository.getUserProfileSync(userId)
}
