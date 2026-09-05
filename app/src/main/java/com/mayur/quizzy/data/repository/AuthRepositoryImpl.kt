package com.mayur.quizzy.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.mayur.quizzy.domain.model.auth.AuthUser
import com.mayur.quizzy.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase
) : AuthRepository {

    override fun currentUser(): AuthUser? {
        val user = auth.currentUser ?: return null
        return AuthUser(
            id = user.uid,
            email = user.email,
            displayName = user.displayName
        )
    }

    override suspend fun signIn(email: String, password: String): Result<AuthUser> {
        return runCatching {
            auth.signInWithEmailAndPassword(email, password).await()
            currentUser() ?: error("Sign in succeeded but no user was returned.")
        }
    }

    override suspend fun signUp(name: String, email: String, password: String): Result<AuthUser> {
        return runCatching {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser ?: error("User creation failed. Please try again.")

            if (name.isNotEmpty()) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()
                user.updateProfile(profileUpdates).await()
            }

            runCatching {
                database.reference.child("users").child(user.uid).setValue(
                    hashMapOf(
                        "name" to name,
                        "email" to email,
                        "uid" to user.uid
                    )
                ).await()
            }

            currentUser() ?: AuthUser(id = user.uid, email = email, displayName = name)
        }
    }

    override suspend fun sendPasswordReset(email: String): Result<Unit> {
        return runCatching {
            auth.sendPasswordResetEmail(email).await()
        }
    }

    override fun signOut() {
        auth.signOut()
    }
}
