package com.mayur.quizzy.domain.repository

import com.mayur.quizzy.domain.model.quiz.QuizAttempt
import com.mayur.quizzy.domain.model.quiz.QuizStatistics
import com.mayur.quizzy.domain.model.user.UserProfile
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    // Quiz Attempts
    fun getAllAttempts(): Flow<List<QuizAttempt>>
    fun getAttemptsByQuizId(quizId: String): Flow<List<QuizAttempt>>
    suspend fun insertAttempt(attempt: QuizAttempt): Long
    suspend fun insertAttempts(attempts: List<QuizAttempt>)
    suspend fun deleteAttempt(attempt: QuizAttempt)
    suspend fun deleteAllAttempts()
    suspend fun deleteAttemptsByQuizId(quizId: String)
    
    // Statistics
    fun getTotalAttemptsCount(): Flow<Int>
    fun getTotalCorrectAnswers(): Flow<Int>
    fun getTotalQuestionsAttempted(): Flow<Int>
    fun getAverageScore(): Flow<Double>
    fun getQuizStatistics(): Flow<QuizStatistics>
    suspend fun getQuestionsAttemptedByQuiz(): Map<String, Int>
    suspend fun getAttemptsCountByQuiz(): Map<String, Int>
    
    // User Profile
    fun getUserProfile(userId: String): Flow<UserProfile?>
    suspend fun getUserProfileSync(userId: String): UserProfile?
    suspend fun saveUserProfile(profile: UserProfile)
    suspend fun updateUserProfile(profile: UserProfile)
    suspend fun deleteUserProfile(userId: String)
}

