package com.mayur.quizzy.presentation.screens.auth.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.mayur.quizzy.AppPreview
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.ui.theme.QuizzyTheme

@Composable
fun SignUpScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.isSignUpSuccessful) {
        if (state.isSignUpSuccessful) {
            navController.navigate(Graph.Main) {
                popUpTo(Graph.Auth) { inclusive = true }
            }
            viewModel.consumeSignUpSuccess()
        }
    }

    LaunchedEffect(state.errorMessage, state.infoMessage) {
        val errorMessage = state.errorMessage
        val infoMessage = state.infoMessage
        if (errorMessage != null) {
            snackbarHostState.showSnackbar(errorMessage)
        }
        if (infoMessage != null) {
            snackbarHostState.showSnackbar(infoMessage)
        }
        if (errorMessage != null || infoMessage != null) {
            viewModel.clearMessages()
        }
    }

    SignUpScreenContent(
        modifier = modifier,
        state = state,
        snackbarHostState = snackbarHostState,
        onNameChange = viewModel::onDisplayNameChanged,
        onEmailChange = viewModel::onEmailChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onConfirmPasswordChange = viewModel::onConfirmPasswordChanged,
        onTogglePassword = viewModel::togglePasswordVisibility,
        onToggleConfirmPassword = viewModel::toggleConfirmPasswordVisibility,
        onSignUpClick = viewModel::signUp,
        onLoginClick = { navController.navigate(Routes.Login) { popUpTo(Routes.Login) { inclusive = true } } }
    )
}

@Composable
private fun SignUpScreenContent(
    modifier: Modifier,
    state: SignUpState,
    snackbarHostState: SnackbarHostState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTogglePassword: () -> Unit,
    onToggleConfirmPassword: () -> Unit,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    val passwordsMatch = state.password.isNotEmpty() &&
        state.confirmPassword.isNotEmpty() &&
        state.password == state.confirmPassword

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Surface(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
            ) {
                
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Create your account",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = state.displayName,
                    onValueChange = onNameChange,
                    label = { Text("Full name") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = null) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                    singleLine = true,
                    enabled = !state.isLoading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChange,
                    label = { Text("Password") },
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = onTogglePassword) {
                            Icon(
                                imageVector = if (state.isPasswordVisible) {
                                    Icons.Filled.VisibilityOff
                                } else {
                                    Icons.Filled.Visibility
                                },
                                contentDescription = if (state.isPasswordVisible) {
                                    "Hide password"
                                } else {
                                    "Show password"
                                }
                            )
                        }
                    },
                    visualTransformation = if (state.isPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    singleLine = true,
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    isError = state.confirmPassword.isNotEmpty() && !passwordsMatch,
                    label = { Text("Confirm password") },
                    supportingText = {
                        if (state.confirmPassword.isNotEmpty() && !passwordsMatch) {
                            Text("Passwords don’t match")
                        }
                    },
                    leadingIcon = { Icon(Icons.Outlined.Lock, contentDescription = null) },
                    trailingIcon = {
                        IconButton(onClick = onToggleConfirmPassword) {
                            Icon(
                                imageVector = if (state.isConfirmPasswordVisible) {
                                    Icons.Filled.VisibilityOff
                                } else {
                                    Icons.Filled.Visibility
                                },
                                contentDescription = if (state.isConfirmPasswordVisible) {
                                    "Hide confirm password"
                                } else {
                                    "Show confirm password"
                                }
                            )
                        }
                    },
                    visualTransformation = if (state.isConfirmPasswordVisible) {
                        VisualTransformation.None
                    } else {
                        PasswordVisualTransformation()
                    },
                    singleLine = true,
                    enabled = !state.isLoading,
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = onSignUpClick,
                    enabled = !state.isLoading && passwordsMatch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text("Sign up")
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Already have an account? ")
                    Text(
                        "Login",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(onClick = onLoginClick)
                    )
                }
            }
        }
    }
}

@AppPreview
@Composable
private fun SignUpScreenPreview() {
    QuizzyTheme {
        SignUpScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = SignUpState(),
            snackbarHostState = SnackbarHostState(),
            onNameChange = {},
            onEmailChange = {},
            onPasswordChange = {},
            onConfirmPasswordChange = {},
            onTogglePassword = {},
            onToggleConfirmPassword = {},
            onSignUpClick = {},
            onLoginClick = {}
        )
    }
}
