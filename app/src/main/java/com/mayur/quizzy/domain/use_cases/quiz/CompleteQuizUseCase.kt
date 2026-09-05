package com.mayur.quizzy.domain.use_cases.quiz

import com.mayur.quizzy.domain.model.quiz.questions.Question
import com.mayur.quizzy.domain.model.quiz.questions.QuestionAnswer
import com.mayur.quizzy.domain.model.quiz.QuizAttempt
import com.mayur.quizzy.domain.model.quiz.QuizResult
import javax.inject.Inject

class CompleteQuizUseCase @Inject constructor(
    private val saveQuizAttempt: SaveQuizAttemptUseCase
) {
    suspend operator fun invoke(
        quizId: String,
        quizName: String,
        questions: List<Question>,
        userAnswers: Map<Int, Int>,
        timeTakenSeconds: Long
    ): QuizResult {
        var correctAnswers = 0
        var wrongAnswers = 0
        val questionAnswers = questions.mapIndexed { index, question ->
            val userAnswer = userAnswers[index]
            val isCorrect = userAnswer != null && userAnswer == question.correctAnswerIndex
            if (isCorrect) correctAnswers++ else wrongAnswers++
            QuestionAnswer(
                question = question,
                userAnswerIndex = userAnswer,
                isCorrect = isCorrect
            )
        }

        val score = if (questions.isNotEmpty()) {
            (correctAnswers * 100) / questions.size
        } else {
            0
        }

        runCatching {
            saveQuizAttempt(
                QuizAttempt(
                    quizId = quizId,
                    quizName = quizName,
                    totalQuestions = questions.size,
                    correctAnswers = correctAnswers,
                    wrongAnswers = wrongAnswers,
                    timeTaken = timeTakenSeconds,
                    score = score
                )
            )
        }

        return QuizResult(
            totalQuestions = questions.size,
            correctAnswers = correctAnswers,
            wrongAnswers = wrongAnswers,
            timeTaken = timeTakenSeconds,
            questionAnswers = questionAnswers
        )
    }
}
