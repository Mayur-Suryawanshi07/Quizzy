package com.mayur.quizzy.data.repository

import com.mayur.quizzy.data.local.dao.UserProfileDao
import com.mayur.quizzy.data.mapper.toUserProfile
import com.mayur.quizzy.data.mapper.toEntity
import com.mayur.quizzy.domain.model.profile.UserProfile
import com.mayur.quizzy.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao
) : UserProfileRepository {

    override fun getUserProfile(userId: String): Flow<UserProfile?> =
        userProfileDao.getUserProfile(userId).map { it?.toUserProfile() }

    override suspend fun getUserProfileSync(userId: String): UserProfile? =
        userProfileDao.getUserProfileSync(userId)?.toUserProfile()

    override suspend fun saveUserProfile(profile: UserProfile) =
        userProfileDao.insertOrUpdateProfile(profile.toEntity())
}
