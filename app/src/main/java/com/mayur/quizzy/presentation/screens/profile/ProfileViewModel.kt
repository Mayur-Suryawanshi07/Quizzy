package com.mayur.quizzy.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.domain.repository.AuthRepository
import com.mayur.quizzy.domain.use_cases.GetQuizStatisticsUseCase
import com.mayur.quizzy.domain.use_cases.GetUserProfileUseCase
import com.mayur.quizzy.domain.use_cases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getQuizStatistics: GetQuizStatisticsUseCase,
    private val getUserProfile: GetUserProfileUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val userId = authRepository.currentUser()?.id ?: "default_user"

    init {
        loadStatistics()
        loadProfile()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            getQuizStatistics().collect { stats ->
                _uiState.value = _uiState.value.copy(
                    totalAttempts = stats.totalAttempts,
                    totalCorrectAnswers = stats.totalCorrectAnswers,
                    totalQuestionsAttempted = stats.totalQuestionsAttempted,
                    averageScore = stats.averageScore
                )
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            getUserProfile.observe(userId).collect { profile ->
                val currentUser = authRepository.currentUser()
                _uiState.value = _uiState.value.copy(
                    profileName = profile?.name?.takeIf { it.isNotBlank() }
                        ?: currentUser?.displayName
                        ?: currentUser?.email?.substringBefore("@")
                        ?: "Guest",
                    profileDescription = profile?.description ?: ""
                )
            }
        }
    }

    fun signOut(onSignedOut: () -> Unit) {
        viewModelScope.launch {
            signOutUseCase()
            onSignedOut()
        }
    }
}
