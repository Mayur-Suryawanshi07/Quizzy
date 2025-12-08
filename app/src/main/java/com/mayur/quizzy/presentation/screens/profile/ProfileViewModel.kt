package com.mayur.quizzy.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.mayur.quizzy.domain.repository.IQuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val quizRepository: IQuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: "default_user"

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

    fun signOut(onSignedOut: () -> Unit) {
        viewModelScope.launch {
            // Reset statistics by clearing local database
            quizRepository.deleteAllAttempts()
            auth.signOut()
            onSignedOut()
        }
    }
}
