package com.mayur.quizzy.domain.repository

import com.mayur.quizzy.domain.model.chatbot.ChatMessage

interface ChatBotRepository {
    suspend fun generateReply(conversation: List<ChatMessage>): Result<String>
}