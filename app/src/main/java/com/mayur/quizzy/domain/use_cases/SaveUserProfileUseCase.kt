package com.mayur.quizzy.domain.use_cases

import com.mayur.quizzy.domain.model.user.UserProfile
import com.mayur.quizzy.domain.repository.UserProfileRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile) = repository.saveUserProfile(profile)
}
