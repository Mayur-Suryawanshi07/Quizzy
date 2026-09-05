package com.mayur.quizzy.data.repository

import com.mayur.quizzy.data.mapper.toApiContent
import com.mayur.quizzy.data.model.chatbot.GenerateContentRequest
import com.mayur.quizzy.data.model.chatbot.GenerateContentResponse
import com.mayur.quizzy.di.GeminiApiKey
import com.mayur.quizzy.domain.model.chatbot.ChatMessage
import com.mayur.quizzy.domain.repository.ChatBotRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ChatBotRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    @GeminiApiKey private val apiKey: String
) : ChatBotRepository {

    override suspend fun generateReply(conversation: List<ChatMessage>): Result<String> {
        val modelName = "gemini-3.8-flash"
        val apiUrl =
            "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=$apiKey"

        return runCatching {
            val response: GenerateContentResponse = httpClient.post(apiUrl) {
                contentType(ContentType.Application.Json)
                setBody(GenerateContentRequest(contents = conversation.map { it.toApiContent() }))
            }.body()

            if (response.error != null) {
                error(response.error.message ?: "API Error: ${response.error.code}")
            }

            val text = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
            if (text.isNullOrBlank()) {
                error("No response from the AI. Please try again.")
            }
            text
        }
    }
}