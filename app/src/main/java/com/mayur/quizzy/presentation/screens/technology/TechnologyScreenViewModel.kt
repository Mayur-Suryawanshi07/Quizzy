package com.mayur.quizzy.presentation.screens.technology

import androidx.lifecycle.ViewModel
import com.mayur.quizzy.domain.use_cases.technology.GetTechnologyQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TechnologyScreenViewModel @Inject constructor(
    private val getTechnologyQuizzesUseCase: GetTechnologyQuizzesUseCase
) : ViewModel() {

    fun getTechnologyQuizzes(): List<TechnologyQuizUi> {
        return getTechnologyQuizzesUseCase().map { it.toUi() }
    }
}
