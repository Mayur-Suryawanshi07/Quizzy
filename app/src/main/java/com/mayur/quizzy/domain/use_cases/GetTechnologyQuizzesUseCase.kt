package com.mayur.quizzy.domain.use_cases

import com.mayur.quizzy.domain.model.quiz.TechnologyQuiz
import com.mayur.quizzy.domain.repository.TechnologyQuizRepository
import javax.inject.Inject

class GetTechnologyQuizzesUseCase @Inject constructor(
    private val repository: TechnologyQuizRepository
) {
    operator fun invoke(): List<TechnologyQuiz> = repository.getTechnologyQuizzes()
}

