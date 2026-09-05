package com.mayur.quizzy.data.mapper

import com.mayur.quizzy.data.local.entity.QuizAttemptEntity
import com.mayur.quizzy.data.local.entity.UserProfileEntity
import com.mayur.quizzy.domain.model.quiz.QuizAttempt
import com.mayur.quizzy.domain.model.profile.UserProfile

fun QuizAttemptEntity.toUserProfile(): QuizAttempt {
    return QuizAttempt(
        id = id,
        quizId = quizId,
        quizName = quizName,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        timeTaken = timeTaken,
        score = score,
        completedAt = completedAt
    )
}

fun QuizAttempt.toQuizAttemptEntity(): QuizAttemptEntity {
    return QuizAttemptEntity(
        id = id,
        quizId = quizId,
        quizName = quizName,
        totalQuestions = totalQuestions,
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        timeTaken = timeTaken,
        score = score,
        completedAt = completedAt
    )
}

fun UserProfileEntity.toUserProfile(): UserProfile {
    return UserProfile(
        userId = userId,
        name = name,
        description = description,
        updatedAt = updatedAt
    )
}

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        userId = userId,
        name = name,
        description = description,
        updatedAt = updatedAt
    )
}

