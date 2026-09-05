package com.mayur.quizzy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mayur.quizzy.data.local.dao.QuizAttemptDao
import com.mayur.quizzy.data.local.dao.UserProfileDao
import com.mayur.quizzy.data.local.entity.QuizAttemptEntity
import com.mayur.quizzy.data.local.entity.UserProfileEntity

@Database(
    entities = [QuizAttemptEntity::class, UserProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizAttemptDao(): QuizAttemptDao
    abstract fun userProfileDao(): UserProfileDao
    
    companion object {
        const val DATABASE_NAME = "quiz_database"
    }
}

