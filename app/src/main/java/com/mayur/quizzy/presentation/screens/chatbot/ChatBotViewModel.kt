package com.mayur.quizzy.presentation.screens.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.common.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class GenerateContentRequest(val contents: List<Content>)

@Serializable
data class Content(val parts: List<Part>, val role: String? = null)

@Serializable
data class Part(val text: String)

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean
)

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate> = emptyList(),
    val error: ApiError? = null
)

@Serializable
data class Candidate(val content: Content)

@Serializable
data class ApiError(
    val code: Int? = null,
    val message: String? = null,
    val status: String? = null
)

sealed interface ChatState {
    object Initial : ChatState
    object Loading : ChatState
    data class Success(val response: String) : ChatState
    data class Error(val error: String) : ChatState
}

class ChatBotViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ChatState>(ChatState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    private val conversationHistory = mutableListOf<Content>()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        coerceInputValues = true
    }

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _uiState.value = ChatState.Loading
            
            // Add user message to UI + conversation history
            _messages.update { current ->
                current + ChatMessage(message, isFromUser = true)
            }
            conversationHistory.add(Content(parts = listOf(Part(text = message)), role = "user"))
            
            try {
                // Default to gemini-pro for broader availability (update if you have access to newer models)
                val modelName = "gemini-pro"
                val apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/$modelName:generateContent?key=${Constants.apiKey}"
                
                val httpResponse = client.post(apiUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        GenerateContentRequest(
                            contents = conversationHistory
                        )
                    )
                }
                
                // Check if the response is successful
                if (httpResponse.status == HttpStatusCode.OK) {
                    val response: GenerateContentResponse = httpResponse.body()
                    
                    // Check if API returned an error
                    if (response.error != null) {
                        val errorMessage = response.error.message ?: "API Error: ${response.error.code}"
                        // Remove the user message from history if request failed
                        if (conversationHistory.isNotEmpty() && conversationHistory.last().role == "user") {
                            conversationHistory.removeLast()
                        }
                        _uiState.value = ChatState.Error(errorMessage)
                        return@launch
                    }
                    
                    val responseText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                    
                    if (responseText.isNullOrBlank()) {
                        // Remove the user message from history if no response
                        if (conversationHistory.isNotEmpty() && conversationHistory.last().role == "user") {
                            conversationHistory.removeLast()
                        }
                        _uiState.value = ChatState.Error("No response from the AI")
                        return@launch
                    }
                    
                    // Add bot response to conversation history and UI
                    conversationHistory.add(Content(parts = listOf(Part(text = responseText)), role = "model"))
                    _messages.update { current ->
                        current + ChatMessage(responseText, isFromUser = false)
                    }
                    _uiState.value = ChatState.Success(responseText)
                } else {
                    // Handle non-OK HTTP status
                    val errorMessage = when (httpResponse.status.value) {
                        404 -> {
                            "API endpoint not found (404). Possible issues:\n" +
                            "1. API key may be invalid or expired\n" +
                            "2. Model name may be incorrect\n" +
                            "3. API endpoint may have changed\n\n" +
                            "Please verify your API key at: https://makersuite.google.com/app/apikey"
                        }
                        401 -> {
                            "Unauthorized (401). Your API key is invalid or expired.\n" +
                            "Get a new key at: https://makersuite.google.com/app/apikey"
                        }
                        403 -> {
                            "Access forbidden (403). Please check:\n" +
                            "1. API key permissions\n" +
                            "2. Billing/quota status\n" +
                            "3. API access enabled in Google Cloud Console"
                        }
                        429 -> {
                            "Rate limit exceeded (429). Too many requests.\n" +
                            "Please wait a moment and try again."
                        }
                        else -> "HTTP Error: ${httpResponse.status.value} - ${httpResponse.status.description}"
                    }
                    // Remove the user message from history if request failed
                    if (conversationHistory.isNotEmpty() && conversationHistory.last().role == "user") {
                        conversationHistory.removeLast()
                    }
                    _messages.update { current ->
                        current + ChatMessage(errorMessage, isFromUser = false)
                    }
                    _uiState.value = ChatState.Error(errorMessage)
                }
            } catch (e: Exception) {
                // Remove the user message from history if request failed
                if (conversationHistory.isNotEmpty() && conversationHistory.last().role == "user") {
                    conversationHistory.removeLast()
                }
                val errorMessage = when {
                    e.message?.contains("404") == true -> {
                        "API endpoint not found (404). Possible issues:\n" +
                        "1. API key may be invalid or expired\n" +
                        "2. Model name may be incorrect\n" +
                        "3. API endpoint may have changed\n\n" +
                        "Please verify your API key at: https://makersuite.google.com/app/apikey"
                    }
                    e.message?.contains("401") == true -> {
                        "Unauthorized (401). Your API key is invalid or expired.\n" +
                        "Get a new key at: https://makersuite.google.com/app/apikey"
                    }
                    e.message?.contains("403") == true -> {
                        "Access forbidden (403). Please check:\n" +
                        "1. API key permissions\n" +
                        "2. Billing/quota status\n" +
                        "3. API access enabled in Google Cloud Console"
                    }
                    e.message?.contains("429") == true -> {
                        "Rate limit exceeded (429). Too many requests.\n" +
                        "Please wait a moment and try again."
                    }
                    else -> e.message ?: "An error occurred: ${e.javaClass.simpleName}"
                }
                _messages.update { current ->
                    current + ChatMessage(errorMessage, isFromUser = false)
                }
                _uiState.value = ChatState.Error(errorMessage)
            }
        }
    }

    fun clearConversation() {
        conversationHistory.clear()
        _messages.value = emptyList()
        _uiState.value = ChatState.Initial
    }
}