package com.mayur.quizzy.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz_attempts")
data class QuizAttemptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quizId: String, // e.g., "java", "dbms", "os", "cn"
    val quizName: String, // e.g., "Java Quiz", "DBMS Quiz"
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val timeTaken: Long, // in seconds
    val score: Int, // percentage score
    val completedAt: Long = System.currentTimeMillis()
)

