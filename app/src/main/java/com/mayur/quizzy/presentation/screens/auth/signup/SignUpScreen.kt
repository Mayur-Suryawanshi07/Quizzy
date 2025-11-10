package com.example.quizz.ui.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mayur.quizzy.AppPreview
import com.mayur.quizzy.ui.theme.QuizzyTheme

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var showPass by remember { mutableStateOf(false) }
    var showConfirm by remember { mutableStateOf(false) }

    val passwordsMatch = password.isNotEmpty() && password == confirm

    Surface(modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            Text("Sign up", style = MaterialTheme.typography.headlineMedium)
            Text("Create your account", style = MaterialTheme.typography.bodyMedium)

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                leadingIcon = { Icon(Icons.Filled.Person, null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Outlined.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(if (showPass) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, null)
                    }
                },
                visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                isError = confirm.isNotEmpty() && !passwordsMatch,
                label = { Text("Confirm Password") },
                supportingText = {
                    if (confirm.isNotEmpty() && !passwordsMatch) Text("Passwords don’t match")
                },
                leadingIcon = { Icon(Icons.Outlined.Lock, null) },
                trailingIcon = {
                    IconButton(onClick = { showConfirm = !showConfirm }) {
                        Icon(if (showConfirm) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, null)
                    }
                },
                visualTransformation = if (showConfirm) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { if (passwordsMatch) onSignUp(username.trim(), email.trim(), password) },
                enabled = passwordsMatch && email.isNotBlank() && username.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) { Text("Sign up") }

            Text("Or")

            OutlinedButton(
                onClick = onGoogleClick,
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Login with Google")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Already have an account? ")
                Text(
                    "Login",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }
        }
    }
}


@AppPreview
@Composable
private fun PreviewSignUp() {
    QuizzyTheme() {
        SignUpScreen(onSignUp = { _,_,_ -> }, onLoginClick = {}, onGoogleClick = {})
    }
}
