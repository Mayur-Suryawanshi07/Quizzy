package com.mayur.quizzy.presentation.screens.auth.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.domain.use_cases.auth.SignUpUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    fun onDisplayNameChanged(name: String) {
        updateState { copy(displayName = name, errorMessage = null, infoMessage = null) }
    }

    fun onEmailChanged(email: String) {
        updateState { copy(email = email, errorMessage = null, infoMessage = null) }
    }

    fun onPasswordChanged(password: String) {
        updateState { copy(password = password, errorMessage = null, infoMessage = null) }
    }

    fun onConfirmPasswordChanged(confirm: String) {
        updateState { copy(confirmPassword = confirm, errorMessage = null, infoMessage = null) }
    }

    fun togglePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    fun toggleConfirmPasswordVisibility() {
        updateState { copy(isConfirmPasswordVisible = !isConfirmPasswordVisible) }
    }

    fun signUp() {
        val current = _state.value
        val name = current.displayName.trim()
        val email = current.email.trim()
        val password = current.password
        val confirm = current.confirmPassword

        when {
            name.isBlank() || email.isBlank() || password.isBlank() || confirm.isBlank() -> {
                updateState { copy(errorMessage = "All fields are required.") }
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                updateState { copy(errorMessage = "Enter a valid email address.") }
                return
            }

            password.length < 6 -> {
                updateState { copy(errorMessage = "Password must be at least 6 characters.") }
                return
            }

            password != confirm -> {
                updateState { copy(errorMessage = "Passwords do not match.") }
                return
            }
        }

        updateState { copy(isLoading = true, errorMessage = null, infoMessage = null) }
        viewModelScope.launch {
            signUpUseCase(name, email, password)
                .onSuccess {
                    updateState { copy(isLoading = false, isSignUpSuccessful = true) }
                }
                .onFailure { error ->
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage ?: "Sign up failed. Please try again."
                        )
                    }
                }
        }
    }

    fun consumeSignUpSuccess() {
        if (_state.value.isSignUpSuccessful) {
            updateState { copy(isSignUpSuccessful = false) }
        }
    }

    fun clearMessages() {
        updateState { copy(errorMessage = null, infoMessage = null) }
    }

    private fun updateState(transform: SignUpState.() -> SignUpState) {
        _state.value = _state.value.transform()
    }
}
