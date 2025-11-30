package com.mayur.quizzy.presentation.screens.homescreen

import androidx.lifecycle.ViewModel
import com.mayur.quizzy.domain.model.TechnologyQuiz
import com.mayur.quizzy.domain.repository.ITechnologyQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val technologyQuizRepository: ITechnologyQuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTechnologyQuizzes()
    }

    private fun loadTechnologyQuizzes() {
        _uiState.update { currentState ->
            currentState.copy(technologyQuizzes = technologyQuizRepository.getTechnologyQuizzes())
        }
    }
}