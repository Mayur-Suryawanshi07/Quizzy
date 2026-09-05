package com.mayur.quizzy.domain.model.chatbot

data class ChatMessage(
    val text: String,
    val isFromUser: Boolean
)