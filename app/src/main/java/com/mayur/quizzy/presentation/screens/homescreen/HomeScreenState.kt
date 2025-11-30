package com.mayur.quizzy.presentation.screens.homescreen

import com.mayur.quizzy.domain.model.TechnologyQuiz

data class HomeScreenState(
    val technologyQuizzes: List<TechnologyQuiz> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)