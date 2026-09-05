package com.mayur.quizzy.presentation.screens.chatbot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.quizzy.domain.model.chatbot.ChatMessage
import com.mayur.quizzy.domain.use_cases.GenerateChatReplyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatBotViewModel @Inject constructor(
    private val generateChatReply: GenerateChatReplyUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatBotState>(ChatBotState.Initial)
    val uiState = _uiState.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages = _messages.asStateFlow()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _uiState.value = ChatBotState.Loading
            _messages.update { current ->
                current + ChatMessage(message, isFromUser = true)
            }

            val result = generateChatReply(_messages.value)
            result.fold(
                onSuccess = { responseText ->
                    _messages.update { current ->
                        current + ChatMessage(responseText, isFromUser = false)
                    }
                    _uiState.value = ChatBotState.Success(responseText)
                },
                onFailure = { error ->
                    val errorMessage = error.message ?: error.javaClass.simpleName
                    _messages.update { current ->
                        current + ChatMessage("Error: $errorMessage", isFromUser = false)
                    }
                    _uiState.value = ChatBotState.Error(errorMessage)
                }
            )
        }
    }

    fun clearConversation() {
        _messages.value = emptyList()
        _uiState.value = ChatBotState.Initial
    }
}
