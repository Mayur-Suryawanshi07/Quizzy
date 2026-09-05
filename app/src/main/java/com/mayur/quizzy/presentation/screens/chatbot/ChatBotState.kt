package com.mayur.quizzy.presentation.screens.chatbot

sealed interface ChatBotState {
    object Initial : ChatBotState
    object Loading : ChatBotState
    data class Success(val response: String) : ChatBotState
    data class Error(val error: String) : ChatBotState
}