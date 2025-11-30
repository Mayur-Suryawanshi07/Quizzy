package com.mayur.quizzy.presentation.screens.chatbot

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.common.Constants
import com.mayur.quizzy.domain.repository.chatbot.IChatBotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import javax.inject.Inject

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

@HiltViewModel
class ChatBotViewModel @Inject constructor(
    private val chatBotRepository: IChatBotRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatState>(ChatState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    private val conversationHistory = mutableListOf<Content>()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            // Log the input message
            Log.d("ChatBotViewModel", "User sent message: $message")
            Log.d("ChatBotViewModel", "Message length: ${message.length} characters")
            
            _uiState.value = ChatState.Loading
            
            // Add user message to UI + conversation history
            _messages.update { current ->
                current + ChatMessage(message, isFromUser = true)
            }
            conversationHistory.add(Content(parts = listOf(Part(text = message)), role = "user"))
            
            Log.d("ChatBotViewModel", "Conversation history size: ${conversationHistory.size}")
            Log.d("ChatBotViewModel", "Calling API with ${conversationHistory.size} messages in history")
            
            try {
                val response = chatBotRepository.generateContent(conversationHistory, Constants.apiKey)
                Log.d("ChatBotViewModel", "API response received. Has error: ${response.error != null}, Candidates: ${response.candidates.size}")
                
                // Log full response details for debugging
                if (response.error != null) {
                    Log.e("ChatBotViewModel", "Response error details - Code: ${response.error.code}, Message: ${response.error.message}, Status: ${response.error.status}")
                }
                if (response.candidates.isEmpty()) {
                    Log.w("ChatBotViewModel", "Response has no candidates. Full response structure might be different.")
                }
                
                // Check if API returned an error
                if (response.error != null) {
                    val errorMessage = response.error.message ?: "API Error: ${response.error.code}"
                    Log.e("ChatBotViewModel", "API Error: $errorMessage")
                    Log.e("ChatBotViewModel", "Error code: ${response.error.code}, Status: ${response.error.status}")
                    // Show error message to user
                    _messages.update { current ->
                        current + ChatMessage("Error: $errorMessage", isFromUser = false)
                    }
                    _uiState.value = ChatState.Error(errorMessage)
                    return@launch
                }
                
                val responseText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                Log.d("ChatBotViewModel", "Response text extracted: ${responseText?.take(50)}...")
                
                if (responseText.isNullOrBlank()) {
                    Log.w("ChatBotViewModel", "Response text is null or blank")
                    val errorMsg = "No response from the AI. Please try again."
                    _messages.update { current ->
                        current + ChatMessage(errorMsg, isFromUser = false)
                    }
                    _uiState.value = ChatState.Error(errorMsg)
                    return@launch
                }
                
                // Add bot response to conversation history and UI
                conversationHistory.add(Content(parts = listOf(Part(text = responseText)), role = "model"))
                _messages.update { current ->
                    current + ChatMessage(responseText, isFromUser = false)
                }
                Log.d("ChatBotViewModel", "Successfully added bot response. Response length: ${responseText.length} characters")
                _uiState.value = ChatState.Success(responseText)
            } catch (e: Exception) {
                Log.e("ChatBotViewModel", "Exception occurred: ${e.javaClass.simpleName}", e)
                Log.e("ChatBotViewModel", "Exception message: ${e.message}")
                Log.e("ChatBotViewModel", "Stack trace: ${e.stackTraceToString()}")
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
                    e.message?.contains("Cleartext") == true -> {
                        "Network error: Cleartext HTTP traffic not permitted.\n" +
                        "Please check your network configuration."
                    }
                    else -> "Error: ${e.message ?: e.javaClass.simpleName}\n\nPlease check your internet connection and try again."
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