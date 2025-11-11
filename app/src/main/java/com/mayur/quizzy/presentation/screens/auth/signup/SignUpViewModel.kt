package com.mayur.quizzy.presentation.screens.auth.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
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

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        // Update user profile with display name
                        if (name.isNotEmpty()) {
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                            user.updateProfile(profileUpdates)
                                .addOnCompleteListener {
                                    // Ignore completion result for profile update
                                }
                        }
                        
                        // Store user data in Realtime Database
                        val userRef = database.reference.child("users").child(user.uid)
                        val userData = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "uid" to user.uid
                        )
                        
                        userRef.setValue(userData)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    updateState { copy(isLoading = false, isSignUpSuccessful = true) }
                                } else {
                                    // Even if database write fails, sign up is successful
                                    // User can still use the app, data can be synced later
                                    val dbError = dbTask.exception?.localizedMessage
                                        ?: "User created but failed to save profile data."
                                    updateState { 
                                        copy(
                                            isLoading = false, 
                                            isSignUpSuccessful = true,
                                            infoMessage = dbError
                                        ) 
                                    }
                                }
                            }
                    } else {
                        updateState { 
                            copy(
                                isLoading = false, 
                                errorMessage = "User creation failed. Please try again."
                            ) 
                        }
                    }
                } else {
                    val errorMessage = task.exception?.localizedMessage
                        ?: "Sign up failed. Please try again."
                    updateState { copy(isLoading = false, errorMessage = errorMessage) }
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
