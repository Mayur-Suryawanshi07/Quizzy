package com.mayur.quizzy.domain.use_cases

import com.mayur.quizzy.domain.model.quiz.questions.Question
import com.mayur.quizzy.domain.repository.TechnologyQuizRepository
import javax.inject.Inject

class GetQuizQuestionsUseCase @Inject constructor(
    private val repository: TechnologyQuizRepository
) {
    operator fun invoke(quizId: String, count: Int): List<Question> {
        return repository.getQuestions(quizId).shuffled().take(count)
    }
}
