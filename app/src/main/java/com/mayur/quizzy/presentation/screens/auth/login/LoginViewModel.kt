package com.mayur.quizzy.presentation.screens.auth.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(
        LoginState(
            email = authRepository.currentUser()?.email.orEmpty(),
            isLoginSuccessful = authRepository.currentUser() != null
        )
    )
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun onEmailChanged(email: String) {
        updateState { copy(email = email, errorMessage = null, infoMessage = null) }
    }

    fun onPasswordChanged(password: String) {
        updateState { copy(password = password, errorMessage = null, infoMessage = null) }
    }

    fun togglePasswordVisibility() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    fun login() {
        val current = _state.value
        val email = current.email.trim()
        val password = current.password

        when {
            email.isBlank() || password.isBlank() -> {
                updateState { copy(errorMessage = "Email and password are required.") }
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                updateState { copy(errorMessage = "Enter a valid email address.") }
                return
            }
        }

        updateState { copy(isLoading = true, errorMessage = null, infoMessage = null) }
        viewModelScope.launch {
            authRepository.signIn(email, password)
                .onSuccess {
                    updateState { copy(isLoading = false, isLoginSuccessful = true) }
                }
                .onFailure { error ->
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage ?: "Login failed. Please try again."
                        )
                    }
                }
        }
    }

    fun sendPasswordReset() {
        val email = _state.value.email.trim()
        if (email.isBlank()) {
            updateState { copy(errorMessage = "Enter your email to reset password.") }
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            updateState { copy(errorMessage = "Enter a valid email address.") }
            return
        }

        updateState { copy(isLoading = true, errorMessage = null, infoMessage = null) }
        viewModelScope.launch {
            authRepository.sendPasswordReset(email)
                .onSuccess {
                    updateState { copy(isLoading = false, infoMessage = "Password reset email sent.") }
                }
                .onFailure { error ->
                    updateState {
                        copy(
                            isLoading = false,
                            errorMessage = error.localizedMessage
                                ?: "Unable to send reset email. Try again later."
                        )
                    }
                }
        }
    }

    fun consumeLoginSuccess() {
        if (_state.value.isLoginSuccessful) {
            updateState { copy(isLoginSuccessful = false) }
        }
    }

    fun clearMessages() {
        updateState { copy(errorMessage = null, infoMessage = null) }
    }

    private fun updateState(transform: LoginState.() -> LoginState) {
        _state.value = _state.value.transform()
    }
}
