package com.mayur.quizzy.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mayur.quizzy.data.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: "default_user"

    init {
        loadStatistics()
        loadProfile()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            combine(
                quizRepository.getTotalAttemptsCount(),
                quizRepository.getTotalCorrectAnswers(),
                quizRepository.getTotalQuestionsAttempted(),
                quizRepository.getAverageScore()
            ) { totalAttempts, totalCorrect, totalQuestions, avgScore ->
                _uiState.value = _uiState.value.copy(
                    totalAttempts = totalAttempts,
                    totalCorrectAnswers = totalCorrect,
                    totalQuestionsAttempted = totalQuestions,
                    averageScore = avgScore
                )
            }.collect { }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            quizRepository.getUserProfile(userId).collect { profile ->
                val firebaseUser = FirebaseAuth.getInstance().currentUser
                _uiState.value = _uiState.value.copy(
                    profileName = profile?.name?.takeIf { it.isNotBlank() }
                        ?: firebaseUser?.displayName
                        ?: firebaseUser?.email?.substringBefore("@")
                        ?: "Guest",
                    profileDescription = profile?.description ?: ""
                )
            }
        }
    }
}

data class ProfileUiState(
    val totalAttempts: Int = 0,
    val totalCorrectAnswers: Int = 0,
    val totalQuestionsAttempted: Int = 0,
    val averageScore: Double = 0.0,
    val profileName: String = "",
    val profileDescription: String = ""
)
