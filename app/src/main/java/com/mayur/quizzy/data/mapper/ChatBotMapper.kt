package com.mayur.quizzy.data.mapper

import com.mayur.quizzy.data.model.chatbot.Content
import com.mayur.quizzy.data.model.chatbot.Part
import com.mayur.quizzy.domain.model.chatbot.ChatMessage

fun ChatMessage.toApiContent(): Content {
    return Content(
        parts = listOf(Part(text = text)),
        role = if (isFromUser) "user" else "model"
    )
}
