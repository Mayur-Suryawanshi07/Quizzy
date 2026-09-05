package com.mayur.quizzy.domain.use_cases.chatbot

import com.mayur.quizzy.domain.model.chatbot.ChatMessage
import com.mayur.quizzy.domain.repository.ChatBotRepository
import javax.inject.Inject

class GenerateChatReplyUseCase @Inject constructor(
    private val repository: ChatBotRepository
) {
    suspend operator fun invoke(conversation: List<ChatMessage>): Result<String> {
        return repository.generateReply(conversation)
    }
}