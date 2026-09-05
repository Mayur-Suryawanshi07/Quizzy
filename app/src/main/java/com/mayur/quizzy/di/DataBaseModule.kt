package com.mayur.quizzy.di

import android.content.Context
import androidx.room.Room
import com.mayur.quizzy.data.local.QuizDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {


    @Provides
    @Singleton
    fun provideQuizDatabase(@ApplicationContext context: Context): QuizDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            QuizDatabase::class.java,
            QuizDatabase.DATABASE_NAME
        )
            .build()
    }
    
    @Provides
    fun provideQuizAttemptDao(database: QuizDatabase)
    = database.quizAttemptDao()
    
    @Provides
    fun provideUserProfileDao(database: QuizDatabase)
    = database.userProfileDao()
}