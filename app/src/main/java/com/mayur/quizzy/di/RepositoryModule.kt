package com.mayur.quizzy.di

import com.mayur.quizzy.data.repository.AuthRepositoryImpl
import com.mayur.quizzy.data.repository.ChatBotRepositoryImpl
import com.mayur.quizzy.data.repository.QuizAttemptRepositoryImpl
import com.mayur.quizzy.data.repository.TechnologyQuizRepositoryImpl
import com.mayur.quizzy.data.repository.UserProfileRepositoryImpl
import com.mayur.quizzy.domain.repository.AuthRepository
import com.mayur.quizzy.domain.repository.ChatBotRepository
import com.mayur.quizzy.domain.repository.QuizAttemptRepository
import com.mayur.quizzy.domain.repository.TechnologyQuizRepository
import com.mayur.quizzy.domain.repository.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindQuizAttemptRepository(
        impl: QuizAttemptRepositoryImpl
    ): QuizAttemptRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(
        impl: UserProfileRepositoryImpl
    ): UserProfileRepository

    @Binds
    @Singleton
    abstract fun bindTechnologyQuizRepository(
        impl: TechnologyQuizRepositoryImpl
    ): TechnologyQuizRepository

    @Binds
    @Singleton
    abstract fun bindChatBotRepository(
        impl: ChatBotRepositoryImpl
    ): ChatBotRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository
}
