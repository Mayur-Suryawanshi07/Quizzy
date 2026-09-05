package com.mayur.quizzy.data.repository

import com.mayur.quizzy.data.local.dao.QuizAttemptDao
import com.mayur.quizzy.data.local.dao.UserProfileDao
import com.mayur.quizzy.data.mapper.toEntity
import com.mayur.quizzy.data.mapper.toQuizAttemptEntity
import com.mayur.quizzy.data.mapper.toUserProfile
import com.mayur.quizzy.domain.model.quiz.QuizAttempt
import com.mayur.quizzy.domain.model.quiz.QuizStatistics
import com.mayur.quizzy.domain.model.profile.UserProfile
import com.mayur.quizzy.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(
    private val quizAttemptDao: QuizAttemptDao,
    private val userProfileDao: UserProfileDao
) : QuizRepository {
    override fun getAllAttempts(): Flow<List<QuizAttempt>> =
        quizAttemptDao.getAllAttempts().map { entities -> entities.map { it.toUserProfile() } }

    override fun getAttemptsByQuizId(quizId: String): Flow<List<QuizAttempt>> =
        quizAttemptDao.getAttemptsByQuizId(quizId).map { entities -> entities.map { it.toUserProfile() } }

    override suspend fun insertAttempt(attempt: QuizAttempt): Long =
        quizAttemptDao.insertAttempt(attempt.toQuizAttemptEntity())

    override suspend fun insertAttempts(attempts: List<QuizAttempt>) =
        quizAttemptDao.insertAttempts(attempts.map { it.toQuizAttemptEntity() })

    override suspend fun deleteAttempt(attempt: QuizAttempt) =
        quizAttemptDao.deleteAttempt(attempt.toQuizAttemptEntity())

    override suspend fun deleteAllAttempts() = quizAttemptDao.deleteAllAttempts()

    override suspend fun deleteAttemptsByQuizId(quizId: String) =
        quizAttemptDao.deleteAttemptsByQuizId(quizId)

    override fun getTotalAttemptsCount(): Flow<Int> = quizAttemptDao.getTotalAttemptsCount()

    override fun getTotalCorrectAnswers(): Flow<Int> =
        quizAttemptDao.getTotalCorrectAnswers().map { it ?: 0 }

    override fun getTotalQuestionsAttempted(): Flow<Int> =
        quizAttemptDao.getTotalQuestionsAttempted().map { it ?: 0 }

    override fun getAverageScore(): Flow<Double> = quizAttemptDao.getAverageScore().map { it ?: 0.0 }

    override fun getQuizStatistics(): Flow<QuizStatistics> = combine(
        quizAttemptDao.getTotalAttemptsCount(),
        getTotalCorrectAnswers(),
        getTotalQuestionsAttempted(),
        getAverageScore()
    ) { totalAttempts, totalCorrect, totalQuestions, averageScore ->
        QuizStatistics(totalAttempts, totalCorrect, totalQuestions, averageScore)
    }

    override suspend fun getQuestionsAttemptedByQuiz(): Map<String, Int> =
        quizAttemptDao.getQuestionsAttemptedByQuiz().associate { it.quizId to it.total }

    override suspend fun getAttemptsCountByQuiz(): Map<String, Int> =
        quizAttemptDao.getAttemptsCountByQuiz().associate { it.quizId to it.count }

    override fun getUserProfile(userId: String): Flow<UserProfile?> =
        userProfileDao.getUserProfile(userId).map { it?.toUserProfile() }

    override suspend fun getUserProfileSync(userId: String): UserProfile? =
        userProfileDao.getUserProfileSync(userId)?.toUserProfile()

    override suspend fun saveUserProfile(profile: UserProfile) =
        userProfileDao.insertOrUpdateProfile(profile.toEntity())

    override suspend fun updateUserProfile(profile: UserProfile) =
        userProfileDao.updateProfile(profile.toEntity())

    override suspend fun deleteUserProfile(userId: String) = userProfileDao.deleteProfile(userId)
}
