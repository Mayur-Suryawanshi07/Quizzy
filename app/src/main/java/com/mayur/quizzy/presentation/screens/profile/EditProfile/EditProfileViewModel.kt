package com.mayur.quizzy.presentation.screens.profile.EditProfile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.domain.model.user.UserProfile
import com.mayur.quizzy.domain.repository.AuthRepository
import com.mayur.quizzy.domain.use_cases.GetUserProfileUseCase
import com.mayur.quizzy.domain.use_cases.SaveUserProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getUserProfile: GetUserProfileUseCase,
    private val saveUserProfile: SaveUserProfileUseCase,
    authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    private val userId = authRepository.currentUser()?.id ?: "default_user"

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            val profile = getUserProfile.current(userId)
            _uiState.value = _uiState.value.copy(
                name = profile?.name ?: "",
                description = profile?.description ?: "",
                isLoading = false
            )
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun updateDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }

    fun saveProfile() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true, error = null)
            try {
                saveUserProfile(
                    UserProfile(
                        userId = userId,
                        name = _uiState.value.name.trim(),
                        description = _uiState.value.description.trim(),
                        updatedAt = System.currentTimeMillis()
                    )
                )
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    isSaved = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isSaving = false,
                    error = e.message ?: "Failed to save profile"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
