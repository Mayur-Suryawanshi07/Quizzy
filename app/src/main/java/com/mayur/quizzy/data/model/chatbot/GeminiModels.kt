package com.mayur.quizzy.data.model.chatbot

import kotlinx.serialization.Serializable

/** Request body for Gemini's generateContent endpoint. */
@Serializable
data class GenerateContentRequest(
    val contents: List<Content>
)

@Serializable
data class Content(
    val parts: List<Part>,
    val role: String
)

@Serializable
data class Part(
    val text: String
)

/** Response body returned by Gemini's generateContent endpoint. */
@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate> = emptyList(),
    val error: ApiError? = null
)

@Serializable
data class Candidate(
    val content: Content
)

@Serializable
data class ApiError(
    val code: Int? = null,
    val message: String? = null,
    val status: String? = null,
)
