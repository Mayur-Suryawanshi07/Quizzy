package com.mayur.quizzy.presentation.screens.homescreen

import androidx.lifecycle.ViewModel
import com.mayur.quizzy.domain.use_cases.GetTechnologyQuizzesUseCase
import com.mayur.quizzy.presentation.screens.technology.toUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    getTechnologyQuizzes: GetTechnologyQuizzesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(technologyQuizzes = getTechnologyQuizzes().map { it.toUi() })
        }
    }
}
