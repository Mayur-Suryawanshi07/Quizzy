package com.mayur.quizzy.presentation.screens.auth.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
) : ViewModel() {

    private val _state = MutableStateFlow(
        LoginState(
            email = auth.currentUser?.email.orEmpty(),
            isLoginSuccessful = auth.currentUser != null
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
                updateState {
                    copy(errorMessage = "Email and password are required.")
                }
                return
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                updateState {
                    copy(errorMessage = "Enter a valid email address.")
                }
                return
            }
        }

        updateState { copy(isLoading = true, errorMessage = null, infoMessage = null) }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateState { copy(isLoading = false, isLoginSuccessful = true) }
                } else {
                    val errorMessage = task.exception?.localizedMessage
                        ?: "Login failed. Please try again."
                    updateState { copy(isLoading = false, errorMessage = errorMessage) }
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

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateState {
                        copy(
                            isLoading = false,
                            infoMessage = "Password reset email sent."
                        )
                    }
                } else {
                    val errorMessage = task.exception?.localizedMessage
                        ?: "Unable to send reset email. Try again later."
                    updateState { copy(isLoading = false, errorMessage = errorMessage) }
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