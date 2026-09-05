package com.mayur.quizzy.presentation.screens.homescreen

import com.mayur.quizzy.presentation.screens.technology.TechnologyQuizUi

data class HomeScreenState(
    val technologyQuizzes: List<TechnologyQuizUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
