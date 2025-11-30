package com.mayur.quizzy.presentation.screens.profile.EditProfile

data class EditProfileUiState(
    val name: String = "",
    val description: String = "",
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)