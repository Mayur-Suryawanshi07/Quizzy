package com.mayur.quizzy.presentation.screens.auth.login.forgetpass

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.domain.use_cases.auth.SendPasswordResetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ForgetPasswordUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val infoMessage: String? = null
)

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(
    private val sendPasswordReset: SendPasswordResetUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ForgetPasswordUiState())
    val state: StateFlow<ForgetPasswordUiState> = _state.asStateFlow()

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email, errorMessage = null, infoMessage = null)
    }

    fun sendResetLink() {
        val email = _state.value.email.trim()
        when {
            email.isBlank() -> {
                _state.value = _state.value.copy(errorMessage = "Enter your email to reset password.")
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _state.value = _state.value.copy(errorMessage = "Enter a valid email address.")
                return
            }
        }

        _state.value = _state.value.copy(isLoading = true, errorMessage = null, infoMessage = null)
        viewModelScope.launch {
            sendPasswordReset(email)
                .onSuccess {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        infoMessage = "Reset link sent to your email"
                    )
                }
                .onFailure { error ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = error.localizedMessage ?: "Error sending email"
                    )
                }
        }
    }

    fun consumeInfoMessage() {
        _state.value = _state.value.copy(infoMessage = null)
    }
}
