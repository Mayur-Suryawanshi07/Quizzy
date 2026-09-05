package com.mayur.quizzy.domain.repository

import com.mayur.quizzy.domain.model.quiz.questions.Question
import com.mayur.quizzy.domain.model.quiz.TechnologyQuiz

interface TechnologyQuizRepository {
    fun getTechnologyQuizzes(): List<TechnologyQuiz>
    fun getQuestions(quizId: String): List<Question>
}
