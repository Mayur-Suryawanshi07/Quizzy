package com.mayur.quizzy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mayur.quizzy.data.local.entity.QuizAttemptEntity
import com.mayur.quizzy.data.model.quiz.QuizAttemptCount
import com.mayur.quizzy.data.model.quiz.QuizQuestionsCount
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizAttemptDao {
    
    @Query("SELECT * FROM quiz_attempts ORDER BY completedAt DESC")
    fun getAllAttempts(): Flow<List<QuizAttemptEntity>>
    
    @Query("SELECT * FROM quiz_attempts WHERE quizId = :quizId ORDER BY completedAt DESC")
    fun getAttemptsByQuizId(quizId: String): Flow<List<QuizAttemptEntity>>
    
    @Query("SELECT COUNT(*) FROM quiz_attempts")
    fun getTotalAttemptsCount(): Flow<Int>
    
    @Query("SELECT COUNT(*) FROM quiz_attempts WHERE quizId = :quizId")
    fun getAttemptsCountByQuizId(quizId: String): Flow<Int>
    
    @Query("SELECT SUM(correctAnswers) FROM quiz_attempts")
    fun getTotalCorrectAnswers(): Flow<Int?>
    
    @Query("SELECT SUM(totalQuestions) FROM quiz_attempts")
    fun getTotalQuestionsAttempted(): Flow<Int?>
    
    @Query("SELECT AVG(score) FROM quiz_attempts")
    fun getAverageScore(): Flow<Double?>
    
    @Query("SELECT AVG(score) FROM quiz_attempts WHERE quizId = :quizId")
    fun getAverageScoreByQuizId(quizId: String): Flow<Double?>
    
    @Query("SELECT quizId, COUNT(*) as count FROM quiz_attempts GROUP BY quizId")
    suspend fun getAttemptsCountByQuiz(): List<QuizAttemptCount>
    
    @Query("SELECT quizId, SUM(totalQuestions) as total FROM quiz_attempts GROUP BY quizId")
    suspend fun getQuestionsAttemptedByQuiz(): List<QuizQuestionsCount>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempt(attempt: QuizAttemptEntity): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttempts(attempts: List<QuizAttemptEntity>)
    
    @Delete
    suspend fun deleteAttempt(attempt: QuizAttemptEntity)
    
    @Query("DELETE FROM quiz_attempts")
    suspend fun deleteAllAttempts()
    
    @Query("DELETE FROM quiz_attempts WHERE quizId = :quizId")
    suspend fun deleteAttemptsByQuizId(quizId: String)
}


