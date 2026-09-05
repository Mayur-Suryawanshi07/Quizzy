package com.mayur.quizzy.presentation.screens.auth.login

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
import androidx.navigation.NavHostController
import com.mayur.quizzy.AppPreview
import com.mayur.quizzy.presentation.navigation.Graph
import com.mayur.quizzy.presentation.navigation.Routes
import com.mayur.quizzy.presentation.ui.theme.QuizzyTheme

@Composable
fun LoginScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.isLoginSuccessful) {
        if (state.isLoginSuccessful) {
            navController.navigate(Graph.Main) {
                popUpTo(Graph.Auth) { inclusive = true }
            }
            viewModel.consumeLoginSuccess()
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

    LoginScreenContent(
        modifier = modifier,
        state = state,
        snackbarHostState = snackbarHostState,
        onEmailChange = viewModel::onEmailChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onTogglePassword = viewModel::togglePasswordVisibility,
        onLoginClick = viewModel::login,
        onForgotPassword = { navController.navigate(Routes.ForgetPassword) },
        onSignUpClick = { navController.navigate(Routes.SignUp) }
    )
}

@Composable
private fun LoginScreenContent(
    modifier: Modifier,
    state: LoginState,
    snackbarHostState: SnackbarHostState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onTogglePassword: () -> Unit,
    onLoginClick: () -> Unit,
    onForgotPassword: () -> Unit,
    onSignUpClick: () -> Unit
) {
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
                    text = "Quizzy",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Enter your credentials to continue",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChange,
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
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

                Button(
                    onClick = onLoginClick,
                    enabled = !state.isLoading,
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
                        Text("Login Now")
                    }
                }

                Text(
                    "Forgot password?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable(onClick = onForgotPassword)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Don’t have an account? ")
                    Text(
                        "Sign Up",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(onClick = onSignUpClick)
                    )
                }
            }
        }
    }
}

@AppPreview
@Composable
private fun LoginScreenPreview() {
    QuizzyTheme {
        LoginScreenContent(
            modifier = Modifier.fillMaxSize(),
            state = LoginState(),
            snackbarHostState = SnackbarHostState(),
            onEmailChange = {},
            onPasswordChange = {},
            onTogglePassword = {},
            onLoginClick = {},
            onForgotPassword = {},
            onSignUpClick = {}
        )
    }
}